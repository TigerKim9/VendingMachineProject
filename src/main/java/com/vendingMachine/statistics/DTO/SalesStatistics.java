package com.vendingMachine.statistics.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesStatistics {
	// 일별/월별 매출 통계
	private String period; // 날짜 또는 월
	private Long totalSales; // 총 판매량
	private Long totalRevenue; // 총 매출액

	// 상품별 통계
	private Long productId;
	private String productName;
	private Long salesCount;
	private Long revenue;

	// 자판기별 통계
	private Long vendingMachineId;
	private String vendingMachineName;
	private Long vmSalesCount;
	private Long vmRevenue;
}
