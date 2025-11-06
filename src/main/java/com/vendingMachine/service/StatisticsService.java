package com.vendingMachine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vendingMachine.Mapper.StatisticsMapper;
import com.vendingMachine.statistics.DTO.SalesStatistics;

@Service
public class StatisticsService {

	@Autowired
	private StatisticsMapper statisticsMapper;

	public List<SalesStatistics> getDailySalesStatistics() {
		return statisticsMapper.getDailySalesStatistics();
	}

	public List<SalesStatistics> getMonthlySalesStatistics() {
		return statisticsMapper.getMonthlySalesStatistics();
	}

	public List<SalesStatistics> getProductSalesStatistics() {
		return statisticsMapper.getProductSalesStatistics();
	}

	public List<SalesStatistics> getVendingMachineSalesStatistics() {
		return statisticsMapper.getVendingMachineSalesStatistics();
	}

	public Long getTodayRevenue() {
		return statisticsMapper.getTodayRevenue();
	}

	public Long getMonthRevenue() {
		return statisticsMapper.getMonthRevenue();
	}

	public Long getTotalSalesCount() {
		return statisticsMapper.getTotalSalesCount();
	}
}
