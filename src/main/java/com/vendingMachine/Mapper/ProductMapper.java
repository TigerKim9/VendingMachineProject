package com.vendingMachine.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.vendingMachine.product.DTO.Category;
import com.vendingMachine.product.DTO.Product;

@Mapper
public interface ProductMapper {

	// 상품 등록
	int addProduct(Product product);

	// 상품 수정
	int updateProduct(Product product);

	// 상품 삭제
	int deleteProduct(Long productId);

	// 상품 단건 조회
	Product getProductById(Long productId);

	// 상품 전체 조회
	List<Product> getAllProducts();

	// 카테고리별 상품 조회
	List<Product> getProductsByCategory(Long categoryId);

	// 사용자별 상품 조회
	List<Product> getProductsByUserId(Long userId);

	// 상품명으로 검색
	List<Product> searchProductsByName(String productName);

	// 카테고리 전체 조회
	List<Category> getAllCategories();

	// 카테고리 등록
	int addCategory(Category category);

	// 카테고리 수정
	int updateCategory(Category category);

	// 카테고리 삭제
	int deleteCategory(Long categoryId);
}
