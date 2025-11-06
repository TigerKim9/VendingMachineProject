package com.vendingMachine.statistics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vendingMachine.service.StatisticsService;

@Controller
@RequestMapping("/statistics")
public class StatisticsController {

	@Autowired
	private StatisticsService statisticsService;

	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		model.addAttribute("todayRevenue", statisticsService.getTodayRevenue());
		model.addAttribute("monthRevenue", statisticsService.getMonthRevenue());
		model.addAttribute("totalSales", statisticsService.getTotalSalesCount());
		model.addAttribute("dailyStats", statisticsService.getDailySalesStatistics());
		model.addAttribute("monthlyStats", statisticsService.getMonthlySalesStatistics());
		model.addAttribute("productStats", statisticsService.getProductSalesStatistics());
		model.addAttribute("vmStats", statisticsService.getVendingMachineSalesStatistics());
		return "statistics/dashboard";
	}
}
