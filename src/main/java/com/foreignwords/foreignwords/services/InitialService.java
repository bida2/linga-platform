package com.foreignwords.foreignwords.services;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.foreignwords.foreignwords.entities.Role;
import com.foreignwords.foreignwords.entities.UnapprovedWord;
import com.foreignwords.foreignwords.entities.User;
import com.foreignwords.foreignwords.entities.Word;
import com.foreignwords.foreignwords.repositories.RoleRepository;
import com.foreignwords.foreignwords.repositories.UnapprovedWordRepository;
import com.foreignwords.foreignwords.repositories.UserRepository;
import com.foreignwords.foreignwords.repositories.WordRepository;

@Service
public class InitialService {
	
	@Autowired
	private WordRepository wordRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private UnapprovedWordRepository unapprovedWordRepo;
	
	@Autowired
	private BCryptPasswordEncoder bCrypt;
	
	@Autowired
	private Environment env;
	
	@PostConstruct
	public void init() {
		// If the admin user has been created in DB, then this method should not be fully executed
		try {
		User testUser = userRepo.findByUsername("keloman").get();
		if (testUser != null) {
			return;
		} } catch (NoSuchElementException e) {
			System.out.println("User doesn't exist, execute init method logic below!");
		}
		List<Word> words = new ArrayList<Word>();
		// Add words for testing
		Word internt = new Word("интернешънъл","световно популярен","international","Калин е интернешънъл звезда на бокса!");
		words.add(internt);
		Word workshop = new Word("уъркшоп","практическо занимание в дадена област","workshop","Студентите от РУ Ангел Кънчев проведоха уъркшоп в ФААК България!");
		words.add(workshop);
		Word outsourcing = new Word("аутсорсинг","задаване на работа на фирма извън границите на държавата, в която оперира фирмата-задател","outsourcing","Проект 'Кобра' бе аутсорснат в Индия!");
		words.add(outsourcing);
		Word cloud = new Word("клауд","технология, която е изнесена в онлайн платформа. платформата се състои от хиляди компютри, разпространени по цял свят. позната и като 'облак'","cloud","Гугъл Драйв е клауд-базирана технология!");
		words.add(cloud);
		Word streaming = new Word("стрийминг","поточно предаване на данни в реално време - използва се най-често за зареждане на видео - примери са YouTube и Vbox7","streaming","Петър Калинов е известен със своя стрийминг канал в YouTube!");
		words.add(streaming);
		Word emoji = new Word("емоуджи","картинка с малки размери, която най-често има за цел предаването на основни емоции - тъга, радост, гняв и т.н.","emoji","Изпрати ми тъжното емоуджи!");
		words.add(emoji);
		@SuppressWarnings("serial")
		Word altSpellingsTest = new Word("тех съпорт", "Техническа поддръжка - повечето модерни приложения имат специални екипи, които се справят с проблемите, с които потребителите се сблъскват", "tech support", "Моля, обърнете се към нашия Тех Съпорт екип!", new HashSet<String>() {
			{
				add("тек съпорт");
				add("тех сапорт");
				add("тек сапорт");
			}
		}); 
		words.add(altSpellingsTest);
		wordRepo.saveAll(words);
		
		// Add the a normal user and an admin user
		User admin = new User(env.getProperty("fw.admin.user.username"),bCrypt.encode(env.getProperty("fw.admin.user.password")), env.getProperty("fw.admin.user.email"));
		User user = new User(env.getProperty("fw.normal.user.username"),bCrypt.encode(env.getProperty("fw.normal.user.password")), env.getProperty("fw.normal.user.email"));
		Set<Role> adminRoles = new HashSet<Role>();
		Set<Role> userRoles = new HashSet<Role>();
		adminRoles.add(new Role("ADMIN"));
		adminRoles.add(new Role("USER"));
		userRoles.add(new Role("USER"));
		admin.setRoles(adminRoles);
		user.setRoles(userRoles);
		userRepo.save(admin);
		userRepo.save(user);
		roleRepo.saveAll(adminRoles);
		roleRepo.saveAll(userRoles);
		
		// Create unapproved words for testing
		UnapprovedWord wordOne = new UnapprovedWord("смарт","умен, интелигентен","smart","Петър си купи нов смартфон!");
		UnapprovedWord wordTwo = new UnapprovedWord("тъчскрийн","електронно устройство, чийто екран позволява контрол чрез допир","touchscreen","Новият Huawei телефон е с 5.2 инча тъчскрийн!");
		unapprovedWordRepo.save(wordOne);
		unapprovedWordRepo.save(wordTwo); 
	}

}
