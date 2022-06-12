package com.vendingMachine.product.DTO;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProducStock {

	//제품 id
	private Long productId;
	
	//제품 재고
	private int productQuantity;
	
	//재고 변화 날짜
	private LocalDateTime quantityChangeTime;
	
	/*
	 * 재고 변화 종류 
	 * 
	 * 0 : 판매 (-) 
	 * 1 : 상품재고추가 (+)
	 * 2 : 폐기 (-)
	 *
	 */
	private short productQuantityStatus;
	
	
}
