package com.vendingMachine.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vendingMachine.statistics.DTO.SalesStatistics;

@Mapper
public interface StatisticsMapper {
	// 일별 매출 통계
	List<SalesStatistics> getDailySalesStatistics();

	// 월별 매출 통계
	List<SalesStatistics> getMonthlySalesStatistics();

	// 상품별 판매 통계
	List<SalesStatistics> getProductSalesStatistics();

	// 자판기별 판매 통계
	List<SalesStatistics> getVendingMachineSalesStatistics();

	// 오늘의 매출 합계
	Long getTodayRevenue();

	// 이번달 매출 합계
	Long getMonthRevenue();

	// 총 판매 수량
	Long getTotalSalesCount();
}
