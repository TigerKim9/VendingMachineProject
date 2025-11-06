package com.vendingMachine.inquiry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vendingMachine.Mapper.InquiryMapper;
import com.vendingMachine.config.auth.PrincipalDetails;
import com.vendingMachine.inquiry.DTO.Inquiry;

@Controller
@RequestMapping("/inquiry")
public class InquiryController {

	@Autowired
	private InquiryMapper inquiryMapper;

	@GetMapping("/form")
	public String inquiryForm() {
		return "inquiry/inquiryForm";
	}

	@PostMapping("/submit")
	public String submitInquiry(@RequestParam String title, @RequestParam String content,
			@AuthenticationPrincipal PrincipalDetails principal) {
		if (principal != null) {
			Inquiry inquiry = new Inquiry();
			inquiry.setUserId(principal.getUser().getUid());
			inquiry.setTitle(title);
			inquiry.setContent(content);
			inquiryMapper.addInquiry(inquiry);
		}
		return "redirect:/inquiry/success";
	}

	@GetMapping("/success")
	public String inquirySuccess() {
		return "inquiry/inquirySuccess";
	}

	@GetMapping("/list")
	public String inquiryList(@AuthenticationPrincipal PrincipalDetails principal, Model model) {
		if (principal != null) {
			model.addAttribute("inquiries", inquiryMapper.getInquiriesByUser(principal.getUser().getUid()));
		}
		return "inquiry/inquiryList";
	}
}
