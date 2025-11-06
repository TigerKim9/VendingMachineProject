package com.vendingMachine.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.vendingMachine.config.auth.PrincipalDetails;
import com.vendingMachine.service.HomeService;

@Controller
@RequestMapping("/user")
public class UserPageController {
	@Autowired
	private HomeService homeService;

	@GetMapping("/mypage")
	public String myPage(@AuthenticationPrincipal PrincipalDetails principal, Model model) {
		if (principal != null) {
			model.addAttribute("user", homeService.myPage(principal.getUser().getUid()));
		}
		return "user/mypage";
	}

	@GetMapping("/edit")
	public String editUserForm(@AuthenticationPrincipal PrincipalDetails principal, Model model) {
		if (principal != null) {
			model.addAttribute("user", homeService.myPage(principal.getUser().getUid()));
		}
		return "user/editUser";
	}

	@GetMapping("/changePassword")
	public String changePasswordForm() {
		return "user/changePassword";
	}
}
