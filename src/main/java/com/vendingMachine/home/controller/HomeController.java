package com.vendingMachine.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	//메인페이지
	@GetMapping("/main")
	public String mainPage(Model model) {
		
		return "/Lading/main";
	}

}
