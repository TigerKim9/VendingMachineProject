package com.vendingMachine.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
	
	//로그인페이지
	@GetMapping("/login")
	public String mainPage(Model model) {
		
		return "sign/login";
	}

	//회원가입페이지
	@GetMapping("/signUp")
	public String signUp(Model model) {
		
		return "sign_up";
	}

	//아이디 비밀번호 찾기 페이지
	@GetMapping("/accFind")
	public String accFind(Model model) {
		
		return "sign/accFind";
	}
	

	
}
