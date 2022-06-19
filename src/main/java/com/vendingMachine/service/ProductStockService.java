package com.vendingMachine.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vendingMachine.Mapper.ProductStockMapper;
import com.vendingMachine.product.DTO.Product;
import com.vendingMachine.product.DTO.ProductStock;

@Service
public class ProductStockService {

	@Resource
	ProductStockMapper productStockMapper;
	
	//상품 재고 추가
	public int addProductStock(Product product) {
		int result = productStockMapper.addProductStock(product);
		return result;
	}
	
	//상품 판매시 재고 감소 + 매출금액 산정
	public int sellProductStock(ProductStock productStock) {
		int result = productStockMapper.sellProductStock(productStock);
		productStockMapper.increaseRevenue(productStock);
		
		return result;
	}
	

	//상품 폐기시 재고 감소 + 총 상품금액 계산
}
