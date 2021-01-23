package com.foreignwords.foreignwords.controllers;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.foreignwords.foreignwords.entities.PasswordResetToken;
import com.foreignwords.foreignwords.entities.Role;
import com.foreignwords.foreignwords.entities.UnapprovedWord;
import com.foreignwords.foreignwords.entities.User;
import com.foreignwords.foreignwords.entities.Word;
import com.foreignwords.foreignwords.exceptions.NoSuchUnapprovedWordException;
import com.foreignwords.foreignwords.exceptions.UsernameOrEmailExistsException;
import com.foreignwords.foreignwords.repositories.PasswordResetTokenRepository;
import com.foreignwords.foreignwords.repositories.UnapprovedWordRepository;
import com.foreignwords.foreignwords.repositories.UserRepository;
import com.foreignwords.foreignwords.repositories.WordRepository;
import com.foreignwords.foreignwords.repositories.WordSearchRepository;
import com.foreignwords.foreignwords.services.EmailService;
import com.foreignwords.foreignwords.services.UserService;
import com.foreignwords.foreignwords.validations.CredentialsValidation;

@Controller
public class MainController {

	
	
	@Autowired
	private WordRepository wordRepo;
	
	@Autowired
	private WordSearchRepository wordSearch;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private UnapprovedWordRepository unapprovedWordRepo;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailService mailService;
	
	@Autowired
	private PasswordResetTokenRepository passResetRepo;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcome(Model model) {
		List<Word> randWords = wordRepo.getRandomWords(3);
		model.addAttribute("words", randWords);
		model.addAttribute("isRandom", true);
		return "index";
	}
	
	@RequestMapping(value = "/wordUser", method = RequestMethod.GET)
	public String addWordUser(Model model) {
		model.addAttribute("wordNew",new UnapprovedWord());
		return "wordUser";
	}
	
	@RequestMapping(value = "/wordUser", method = RequestMethod.POST)
	public String addWordUserPost(Model model,@ModelAttribute("wordNew") UnapprovedWord word,BindingResult result) {
		if (!result.hasErrors()) {
			model.addAttribute("successMessage", "Успешно добавихте дума - трябва да бъде удобрена от администратора, за да се добави официално!");
		} else {
			model.addAttribute("errorMessage", "Неуспешно добавяне на дума!");
			return "wordUser";
		}
		unapprovedWordRepo.save(word);
		model.addAttribute("wordNew", new UnapprovedWord());
		return "wordUser";
	}
	
	@RequestMapping(value="/pronounceText", method=RequestMethod.GET)
	public String pronounce() {
		return "pronounceText";
	}

	
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		model.addAttribute("userNew",new User());
		return "signup";
	}
	
	
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signupPost(Model model,@ModelAttribute("userNew") User user,BindingResult result) {
		if (userRepo.findByUsername(user.getUsername()).isPresent() || userRepo.findByEmail(user.getEmail()).isPresent()) {
			throw new UsernameOrEmailExistsException("Съществува потребител с това потребителско име или е-мейл адрес!");
		}
		if (result.hasErrors() || !CredentialsValidation.isUsernameValid(user.getUsername()) 
				|| !CredentialsValidation.isPasswordValid(user.getPassword())
				|| !CredentialsValidation.isEmailValid(user.getEmail())
				|| !CredentialsValidation.doPasswordsMatch(user.getPassword(), user.getPasswordConfirm())) {
			model.addAttribute("errorMessage", "Неуспешна регистрация! Проверете данните си и опитайте отново!");
			return "signup";
		}
		Set<Role> roles = new HashSet<Role>();
		roles.add(new Role("USER"));
		user.setRoles(roles);
		String encPass = encoder.encode(user.getPassword());
		user.setPassword(encPass);
		user.setPasswordConfirm(encPass);
		userRepo.save(user);
		model.addAttribute("successMessage", "Успешно се регистрирахте!");
		model.addAttribute("userNew", new User());
		return "signup";
	}
	
	@RequestMapping(value = "/browseByLetter", method = RequestMethod.GET)
	public String letterBrowse(Model model) {
		char[] bgLetters = "абвгдежзийклмнопрстуфхцшщъьюя".toCharArray();
		model.addAttribute("letters",bgLetters);
		return "browseByLetter";
	}
	
	@RequestMapping(value = "/letterSearch", method = RequestMethod.GET)
	public String letterBrowse(Model model,@RequestParam("letter") String letter) {
		List<Word> wordsByLetter = wordRepo.findByWordStartingWith(letter);
		model.addAttribute("words", wordsByLetter);
		return "letterSearch";
	}
	
	
	@RequestMapping(value = "/word", method = RequestMethod.GET)
	public String addWordAdmin(Model model) {
		model.addAttribute("wordNew",new Word());
		return "word";
	}
	
	@RequestMapping(value = "/word", method = RequestMethod.POST)
	public String addWordAdminPost(Model model,@ModelAttribute("wordNew") Word word,BindingResult result) {
		try {
			wordRepo.save(word);
		} catch (DataIntegrityViolationException e) {
			model.addAttribute("errorMessage", "Тази дума вече съществува в системата!");
			return "word";
		}
		model.addAttribute("successMessage", "Успешно добавихте дума!");
		model.addAttribute("wordNew", new Word());
		return "word";
	}
	
	@RequestMapping(value = "/approve", method = RequestMethod.GET)
	public String approveWord(Model model) {
		// Get all unapproved words to be approved by the admin
		List<UnapprovedWord> words = unapprovedWordRepo.findAll();
		model.addAttribute("unapprovedWords", words);
		return "approveWord";
	}
	
	@RequestMapping(value = "/approve", method = RequestMethod.POST)
	public String approveWordPost(Model model,@RequestParam("wId") Long wordId) {
		// Get the unapproved word first to later insert its data into a new Word instance
		UnapprovedWord unapproved = unapprovedWordRepo.getOne(wordId);
		// Create a new approved Word instance
		try {
			Word approved = new Word(unapproved.getWord(),unapproved.getMeaning(),unapproved.getSpelling(),unapproved.getExampleSent());
			HashSet<String> spellings = new HashSet<String>();
			spellings.addAll(unapproved.getAltSpellings());
			approved.setAltSpellings(spellings); 
			wordRepo.save(approved);
		} catch (DataIntegrityViolationException e) {
			model.addAttribute("errorMessage", "Тази дума вече съществува в системата!");
			return "approveWord";
		}
		unapprovedWordRepo.delete(unapproved);
		model.addAttribute("successMessage", "Думата бе успешно добавена към официалния речник!");
		// Get all unapproved words to be approved by the admin
		List<UnapprovedWord> words = unapprovedWordRepo.findAll();
		model.addAttribute("unapprovedWords", words);
		return "approveWord";
	}
	
	@RequestMapping(value = "/disapprove", method = RequestMethod.POST)
	public String disapproveWordPost(Model model,@RequestParam("wId") Long wordId) throws NoSuchUnapprovedWordException {
		if (wordId == null) {
			model.addAttribute("errorMessage", "Missing data in request!");
		}
		// Get the unapproved word for deletion
		UnapprovedWord unapproved = unapprovedWordRepo.findById(wordId).orElseThrow(() -> new NoSuchUnapprovedWordException());
		unapprovedWordRepo.delete(unapproved);
		// Get all unapproved words to be approved by the admin
		List<UnapprovedWord> words = unapprovedWordRepo.findAll();
		model.addAttribute("unapprovedWords", words);
		return "approveWord";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String welcomePost(@RequestParam("wordSearch") String word,Model model) throws InterruptedException {
		// Does a Hibernate Search search on the indexed field "word" in the Word entity class
		List<Word> words = wordSearch.searchByWord(word);
		if (words.size() == 0) {
			model.addAttribute("noWordsFound", "Няма намерени думи, съдържащи " + "\"" + word + "\"");
		} else {
			model.addAttribute("words", words);
		}
		return "index";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model)  {
		return "login";
	}
	
	@RequestMapping(value = "/forgot", method = RequestMethod.GET)
	public String forgot(Model model)  {
		return "forgot";
	}
	
	@RequestMapping(value = "/loginFailure", method = RequestMethod.GET)
	public String loginFailure(Model model)  {
		model.addAttribute("error", "Грешна парола или потребителско име!");
		return "login";
	}
	
	@RequestMapping(value="/changePassword", method=RequestMethod.GET)
	public String changePassword(@RequestParam(value="token", required=false) String token, Model model) {
		if (CredentialsValidation.isParameterNullOrEmpty(token)) {
			model.addAttribute("message", "Празен или невалиден линк за смяна на паролата!");
			model.addAttribute("disableReset", true);
			return "changePassword";
		}
		try {
			PasswordResetToken resToken = passResetRepo.findByToken(token);
			if (CredentialsValidation.hasPassResetTokenExpired(resToken)) {
				model.addAttribute("message", "Неактивен линк за смяна на паролата! Поискайте нов линк от системата и опитайте пак!");
				model.addAttribute("disableReset", true);
				return "changePassword";
			}
			User changePassUser = resToken.getUser();
			if (CredentialsValidation.isParameterNullOrEmpty(changePassUser)) {
				model.addAttribute("message", "Посочения потребител не съществува!");
				return "changePassword";
			}
			model.addAttribute("user", changePassUser.getUsername());
		} catch (Exception e) {
			model.addAttribute("message", "Възникна грешка - изпратения линк е вече неактивен или неправилно изписан!");
			model.addAttribute("disableReset", true);
			return "changePassword";
		}
		return "changePassword";
	}
	
	
	@RequestMapping(value="/changePassword", method=RequestMethod.POST)
	public String changePasswordPost(@RequestParam("newPassword") String password, @RequestParam("newPasswordConfirm") String confirmPassword, @RequestParam("token") String token, Model model, RedirectAttributes redir) {
		if (CredentialsValidation.hasPassResetTokenExpired(passResetRepo.findByToken(token))) {
			model.addAttribute("message", "Невалиден линк за смяна на паролата! Поискайте нов линк от системата и опитайте пак!");
			model.addAttribute("disableReset", true);
			return "changePassword";
		}
		if (CredentialsValidation.isParameterNullOrEmpty(password)) {
			model.addAttribute("message", "Не сте въвели парола!");
			return "changePassword";
		}
		if (CredentialsValidation.doPasswordsMatch(password, confirmPassword)) {
			model.addAttribute("message", "Въведените пароли не съвпадат!");
			return "changePassword";
		}
		if (!CredentialsValidation.isPasswordValid(password)) {
			model.addAttribute("message", "Неправилен формат на паролата!");
			return "changePassword";
		}
		PasswordResetToken resToken = passResetRepo.findByToken(token);
		User user = resToken.getUser();
		user.setPassword(encoder.encode(password));
		user.setPasswordConfirm(encoder.encode(confirmPassword));
		userRepo.save(user);
		passResetRepo.delete(resToken);
		redir.addAttribute("message", "Успешно променихте паролата си!");
		return "redirect:/";
	}
	
	
	@RequestMapping(value="/forgot", method=RequestMethod.POST)
	public String resetPassword(HttpServletRequest request, 
	  @RequestParam("email") String userEmail, Model model) throws IOException {
		if (!CredentialsValidation.isEmailValid(userEmail)) {
			model.addAttribute("message", "Неправилен формат на е-мейл адреса!");
			return "forgot";
		}
	    User user = userService.findUserByEmail(userEmail);
	     if (user == null) {
	       model.addAttribute("message", "Посочения потребител не съществува!");
	       return "forgot";
	    }
	    String token = UUID.randomUUID().toString();
	    userService.createUserPasswordResetToken(user, token, Date.valueOf(LocalDate.now().plusDays(1l)));
	   /* mailService.sendResetTokenEmail("petar.tonchev18@abv.bg", userEmail, "Dear " + user.getUsername() + 
	    		",\n we would like to inform you that a password change has been requested for your account.\n Your reset link can be found below:\n" +
	    		"<a href='" + request.getScheme() + "://" + request.getServerName() + ":" + request.getLocalPort() + "/changePassword?token=" + token + "'>Click here to reset your password!</a>");
	   */
	    mailService.sendResetTokenEmail("petar.tonchev18@abv.bg", userEmail, "Dear " + user.getUsername() + 
	    		",\n we would like to inform you that a password change has been requested for your account.\n Your reset link can be found below:\n" +
	    		"<a href='" + request.getScheme() + "://" + request.getServerName() + "/changePassword?token=" + token + "'>Click here to reset your password!</a>");
	    model.addAttribute("message", "Е-мейла за смяна на паролата Ви беше изпратен успешно!");
	    return "forgot";
	}
	
	
}
