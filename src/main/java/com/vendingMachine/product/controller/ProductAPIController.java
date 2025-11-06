package com.vendingMachine.product.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vendingMachine.product.DTO.Category;
import com.vendingMachine.product.DTO.Product;
import com.vendingMachine.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductAPIController {

	@Autowired
	private ProductService productService;

	// 상품 등록
	@PostMapping("/register")
	public ResponseEntity<Map<String, Object>> registerProduct(@RequestBody Product product) {
		Map<String, Object> response = new HashMap<>();
		try {
			int result = productService.addProduct(product);
			if (result > 0) {
				response.put("success", true);
				response.put("message", "상품이 등록되었습니다.");
				return ResponseEntity.ok(response);
			} else {
				response.put("success", false);
				response.put("message", "상품 등록에 실패했습니다.");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "오류가 발생했습니다: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	// 상품 수정
	@PutMapping("/update")
	public ResponseEntity<Map<String, Object>> updateProduct(@RequestBody Product product) {
		Map<String, Object> response = new HashMap<>();
		try {
			int result = productService.updateProduct(product);
			if (result > 0) {
				response.put("success", true);
				response.put("message", "상품이 수정되었습니다.");
				return ResponseEntity.ok(response);
			} else {
				response.put("success", false);
				response.put("message", "상품 수정에 실패했습니다.");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "오류가 발생했습니다: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	// 상품 삭제
	@DeleteMapping("/delete/{productId}")
	public ResponseEntity<Map<String, Object>> deleteProduct(@PathVariable Long productId) {
		Map<String, Object> response = new HashMap<>();
		try {
			int result = productService.deleteProduct(productId);
			if (result > 0) {
				response.put("success", true);
				response.put("message", "상품이 삭제되었습니다.");
				return ResponseEntity.ok(response);
			} else {
				response.put("success", false);
				response.put("message", "상품 삭제에 실패했습니다.");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "오류가 발생했습니다: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	// 상품 단건 조회
	@GetMapping("/{productId}")
	public ResponseEntity<Product> getProduct(@PathVariable Long productId) {
		try {
			Product product = productService.getProductById(productId);
			if (product != null) {
				return ResponseEntity.ok(product);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// 상품 전체 조회
	@GetMapping("/list")
	public ResponseEntity<List<Product>> getAllProducts() {
		try {
			List<Product> products = productService.getAllProducts();
			return ResponseEntity.ok(products);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// 카테고리별 상품 조회
	@GetMapping("/category/{categoryId}")
	public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable Long categoryId) {
		try {
			List<Product> products = productService.getProductsByCategory(categoryId);
			return ResponseEntity.ok(products);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// 상품명 검색
	@GetMapping("/search")
	public ResponseEntity<List<Product>> searchProducts(@RequestParam String productName) {
		try {
			List<Product> products = productService.searchProductsByName(productName);
			return ResponseEntity.ok(products);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// 카테고리 전체 조회
	@GetMapping("/categories")
	public ResponseEntity<List<Category>> getAllCategories() {
		try {
			List<Category> categories = productService.getAllCategories();
			return ResponseEntity.ok(categories);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// 카테고리 등록
	@PostMapping("/category/register")
	public ResponseEntity<Map<String, Object>> registerCategory(@RequestBody Category category) {
		Map<String, Object> response = new HashMap<>();
		try {
			int result = productService.addCategory(category);
			if (result > 0) {
				response.put("success", true);
				response.put("message", "카테고리가 등록되었습니다.");
				return ResponseEntity.ok(response);
			} else {
				response.put("success", false);
				response.put("message", "카테고리 등록에 실패했습니다.");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "오류가 발생했습니다: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	// 카테고리 수정
	@PutMapping("/category/update")
	public ResponseEntity<Map<String, Object>> updateCategory(@RequestBody Category category) {
		Map<String, Object> response = new HashMap<>();
		try {
			int result = productService.updateCategory(category);
			if (result > 0) {
				response.put("success", true);
				response.put("message", "카테고리가 수정되었습니다.");
				return ResponseEntity.ok(response);
			} else {
				response.put("success", false);
				response.put("message", "카테고리 수정에 실패했습니다.");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "오류가 발생했습니다: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	// 카테고리 삭제
	@DeleteMapping("/category/delete/{categoryId}")
	public ResponseEntity<Map<String, Object>> deleteCategory(@PathVariable Long categoryId) {
		Map<String, Object> response = new HashMap<>();
		try {
			int result = productService.deleteCategory(categoryId);
			if (result > 0) {
				response.put("success", true);
				response.put("message", "카테고리가 삭제되었습니다.");
				return ResponseEntity.ok(response);
			} else {
				response.put("success", false);
				response.put("message", "카테고리 삭제에 실패했습니다.");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "오류가 발생했습니다: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
}
