package com.vendingMachine.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vendingMachine.Mapper.ProductStockMapper;
import com.vendingMachine.product.DTO.Product;
import com.vendingMachine.product.DTO.ProductStock;

//상품 재고변화 기록 서비스단
@Service
public class ProductStockService {

	@Resource
	ProductStockMapper productStockMapper;
	
	
	
	//상품 재고 추가
	//통계 로직 추가 예정 TODO
	public int addProductStock(Product product) {
		return productStockMapper.addProductStock(product);
		
	}
	
	//상품 판매시 재고 감소 + 매출금액 산정
	//통계 로직 추가 예정 TODO
	public int sellProductStock(ProductStock productStock) {
		int result = productStockMapper.sellProductStock(productStock);
		productStockMapper.increaseRevenue(productStock);
		
		return result;
	}
	

	//상품 폐기시 재고 감소 + 총 상품금액 계산
	public int disposalProductStock(long productId) {
		return productStockMapper.disposalProductStock(productId);
	}
}
