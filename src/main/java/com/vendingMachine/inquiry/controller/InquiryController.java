package com.vendingMachine.inquiry.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/inquiry")
public class InquiryController {

	@GetMapping("/form")
	public String inquiryForm() {
		return "inquiry/inquiryForm";
	}

	@PostMapping("/submit")
	public String submitInquiry(@RequestParam String title, @RequestParam String content) {
		// TODO: 문의 저장 로직
		return "redirect:/inquiry/success";
	}

	@GetMapping("/success")
	public String inquirySuccess() {
		return "inquiry/inquirySuccess";
	}
}
