package com.vendingMachine.vendingMachines;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

// 자판기-상품 배치 Entity
@Getter
@Setter
public class VendingMachineProduct {

	// 자판기-상품 매핑 ID
	private Long vmProductId;

	// 자판기 ID
	private Long vendingMachineId;

	// 상품 ID
	private Long productId;

	// 슬롯 번호
	private Integer slotNumber;

	// 현재 재고
	private Integer currentStock;

	// 최대 재고
	private Integer maxStock;

	// 최소 재고 알림 기준
	private Integer minStockAlert;

	// 배치 날짜
	private LocalDateTime placementDate;

	// 상품명 (JOIN 시 사용)
	private String productName;

	// 판매가 (JOIN 시 사용)
	private String productSellPrice;

	// 자판기명 (JOIN 시 사용)
	private String vendingMachineName;
}
