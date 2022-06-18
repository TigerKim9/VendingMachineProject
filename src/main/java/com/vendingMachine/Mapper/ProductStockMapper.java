package com.vendingMachine.Mapper;

import org.apache.ibatis.annotations.Mapper;

import com.vendingMachine.product.DTO.Product;

@Mapper
public interface ProductStockMapper {


	//상품재고 추가
	int addProductStock(Product product);
	
	//상품 판매시 재고 감소
	int sellProductStock(long productId);

	//상품 폐기시 재고 감소

}
