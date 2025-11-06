package com.vendingMachine.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vendingMachine.product.DTO.Product;
import com.vendingMachine.product.DTO.ProductStock;

@Mapper
public interface ProductStockMapper {


	//상품재고 추가
	int addProductStock(Product product);

	//상품 판매시 재고 감소
	int sellProductStock(ProductStock productStock);

	//상품 폐기시 재고 감소
	int disposalProductStock(long productId);

	//상품 판매시 매출 증가
	int increaseRevenue(ProductStock productStock);

	//재고 변화 이력 전체 조회
	List<ProductStock> getAllStockHistory();

	//특정 상품 재고 변화 이력 조회
	List<ProductStock> getStockHistoryByProduct(Long productId);

	//현재 상품별 재고 조회
	ProductStock getCurrentStock(Long productId);
}
