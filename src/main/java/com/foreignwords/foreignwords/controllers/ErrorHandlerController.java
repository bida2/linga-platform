package com.foreignwords.foreignwords.controllers;


import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorHandlerController implements ErrorController {

	@RequestMapping("/error")
    public String handleError(HttpServletResponse response, Model model) {
		switch (response.getStatus()) {
		case 404: 
			model.addAttribute("errorDesc", "404 - Страницата или ресурса не съществува!");
			break;
		case 500:
			model.addAttribute("errorDesc", "500 - Грешка в сървъра!");
			break;
		case 400:
			model.addAttribute("errorDesc", "400 - Възникна проблем с вашата заявка!");
			break;
		case 403:
			model.addAttribute("errorDesc", "403 - Нямате достъп до страницата!");
			break;
		case 401:
			model.addAttribute("errorDesc", "401 - Нямате права да достъпите страницата!");
			break;
		default:
			model.addAttribute("errorDesc", "Нещо се обърка! Моля, проверете въведените от Вас данни и опитайте отново!");
		}
        return "error";
    }
 
    @Override
    public String getErrorPath() {
        return null;
    }
}
