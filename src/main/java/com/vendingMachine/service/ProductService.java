package com.vendingMachine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vendingMachine.Mapper.ProductMapper;
import com.vendingMachine.product.DTO.Category;
import com.vendingMachine.product.DTO.Product;

@Service
public class ProductService {

	@Autowired
	private ProductMapper productMapper;

	// 상품 등록
	@Transactional
	public int addProduct(Product product) {
		return productMapper.addProduct(product);
	}

	// 상품 수정
	@Transactional
	public int updateProduct(Product product) {
		return productMapper.updateProduct(product);
	}

	// 상품 삭제
	@Transactional
	public int deleteProduct(Long productId) {
		return productMapper.deleteProduct(productId);
	}

	// 상품 단건 조회
	public Product getProductById(Long productId) {
		return productMapper.getProductById(productId);
	}

	// 상품 전체 조회
	public List<Product> getAllProducts() {
		return productMapper.getAllProducts();
	}

	// 카테고리별 상품 조회
	public List<Product> getProductsByCategory(Long categoryId) {
		return productMapper.getProductsByCategory(categoryId);
	}

	// 사용자별 상품 조회
	public List<Product> getProductsByUserId(Long userId) {
		return productMapper.getProductsByUserId(userId);
	}

	// 상품명으로 검색
	public List<Product> searchProductsByName(String productName) {
		return productMapper.searchProductsByName(productName);
	}

	// 카테고리 전체 조회
	public List<Category> getAllCategories() {
		return productMapper.getAllCategories();
	}

	// 카테고리 등록
	@Transactional
	public int addCategory(Category category) {
		return productMapper.addCategory(category);
	}

	// 카테고리 수정
	@Transactional
	public int updateCategory(Category category) {
		return productMapper.updateCategory(category);
	}

	// 카테고리 삭제
	@Transactional
	public int deleteCategory(Long categoryId) {
		return productMapper.deleteCategory(categoryId);
	}
}
