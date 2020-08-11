package br.com.confeitaria.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	
	@GetMapping({"/", "/home"})
	public ModelAndView home() {
		ModelAndView resultado = new ModelAndView("home/home");
		return resultado;
	}
	
	@GetMapping("/login")
	public ModelAndView login() {
		ModelAndView resultado = new ModelAndView("home/login");
		return resultado;
	}
	
//	@GetMapping("/login-error")
//	public String login(HttpServletRequest request, Model model) {
//		HttpSession session = request.getSession(false);
//		String errorMessage = null;
//		if (session != null) {
//			AuthenticationException ex = (AuthenticationException) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
//			if (ex != null) {
//				errorMessage = ex.getMessage();
//			}
//		}
//		model.addAttribute("errorMessage", errorMessage);
//		return "home/login";
//	}

}
