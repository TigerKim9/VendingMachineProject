package com.vendingMachine.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.vendingMachine.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private UserService userService;

	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		return "admin/dashboard";
	}

	@GetMapping("/users")
	public String userManagement(Model model) {
		return "admin/userManagement";
	}
}
