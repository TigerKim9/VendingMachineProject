package com.vendingMachine.product.DTO;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductStock {

	//제품 id
	private Long productId;
	
	//제품 재고변화량
	private int productChangeQuantity;
	
	//제품 재고
	private int productQuantity;
	
	//재고 변화 날짜
	private LocalDateTime quantityChangeTime;
	
	/*
	 * 재고 변화 종류 
	 * 
	 * 0 : 상품재고추가 (+)
	 * 1 : 판매 (-) 
	 * 2 : 폐기 (-)
	 *
	 */
	private short productQuantityStatus;
	
	
}
