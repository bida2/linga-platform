package com.foreignwords.foreignwords.exceptions;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import com.foreignwords.foreignwords.entities.User;


@ControllerAdvice
public class ControllerAdviceLinga extends DefaultHandlerExceptionResolver {
	
	@ExceptionHandler(UsernameOrEmailExistsException.class)
	public String handleUsernameOrEmailExistsException(Model model, UsernameOrEmailExistsException exception) {
		model.addAttribute("errorMessage", exception.getMessage());
		model.addAttribute("userNew", new User());
		return "signup";
	}
	
	@ExceptionHandler(NoSuchUnapprovedWordException.class)
	public String handleNoSuchUnapprovedWordException(Model model, NoSuchUnapprovedWordException exception) {
		model.addAttribute("errorMessage", "No such unapproved word exists!");
		return "approveWord";
	}
	
}
