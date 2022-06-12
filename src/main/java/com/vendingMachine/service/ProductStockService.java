package com.vendingMachine.service;

import org.springframework.stereotype.Service;

@Service
public class ProductStockService {

	//상품 재고 추가
	public int addProductStock() {
		int result = productStockMapper.addProductStock();
		return result;
	}
}
