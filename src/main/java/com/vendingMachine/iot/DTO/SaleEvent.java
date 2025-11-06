package com.vendingMachine.iot.DTO;

import lombok.Getter;
import lombok.Setter;

// 판매 이벤트 데이터
@Getter
@Setter
public class SaleEvent {
	private Long slotNumber;
	private Long productId;
	private Integer quantity;
	private Integer price;
	private String paymentMethod; // CARD, CASH, MOBILE
	private Long timestamp;
}
