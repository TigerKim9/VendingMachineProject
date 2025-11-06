package com.vendingMachine.vendingMachine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vendingMachine.service.VendingMachineService;

@Controller
@RequestMapping("/vending")
public class VendingMachineController {

	@Autowired
	private VendingMachineService vendingMachineService;

	@GetMapping("/list")
	public String vendingMachineList(Model model) {
		model.addAttribute("vendingMachines", vendingMachineService.getAllVendingMachines());
		return "vending/vendingList";
	}

	@GetMapping("/register")
	public String vendingMachineRegisterForm() {
		return "vending/vendingRegister";
	}

	@GetMapping("/edit/{id}")
	public String vendingMachineEditForm(@PathVariable Long id, Model model) {
		model.addAttribute("vendingMachine", vendingMachineService.getVendingMachineById(id));
		return "vending/vendingEdit";
	}
}
