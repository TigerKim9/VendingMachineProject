package com.vendingMachine.product.DTO;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {

	//제품 id
	private Long productId;
	
	//제품을 가지고 있는 유저 Id;
	private Long userId;
	
	//제품이 속한 카테고리
	private Long categoryId;
	
	//제품 이름
	private String productName;
	
	//제품 제조회사
	private String productCompanyId;
	
	//제품 등록 날짜
	private LocalDateTime productRegDate;
	
	
}
