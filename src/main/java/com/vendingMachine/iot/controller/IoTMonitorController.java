package com.vendingMachine.iot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/iot")
public class IoTMonitorController {

	@GetMapping("/monitor")
	public String monitorPage() {
		return "iot/monitor";
	}

	@GetMapping("/guide")
	public String guidePage() {
		return "iot/guide";
	}
}
