package com.vendingMachine.stock.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vendingMachine.product.DTO.Product;
import com.vendingMachine.product.DTO.ProductStock;
import com.vendingMachine.service.ProductStockService;

@RestController
@RequestMapping("/api/stock")
public class StockAPIController {

	@Autowired
	private ProductStockService stockService;

	// 재고 추가
	@PostMapping("/add")
	public ResponseEntity<Map<String, Object>> addStock(@RequestBody Product product) {
		Map<String, Object> response = new HashMap<>();
		try {
			int result = stockService.addProductStock(product);
			if (result > 0) {
				response.put("success", true);
				response.put("message", "재고가 추가되었습니다.");
				return ResponseEntity.ok(response);
			} else {
				response.put("success", false);
				response.put("message", "재고 추가에 실패했습니다.");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "오류가 발생했습니다: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	// 재고 판매
	@PostMapping("/sell")
	public ResponseEntity<Map<String, Object>> sellStock(@RequestBody ProductStock productStock) {
		Map<String, Object> response = new HashMap<>();
		try {
			// 현재 재고 확인
			ProductStock currentStock = stockService.getCurrentStock(productStock.getProductId());
			if (currentStock == null || currentStock.getProductQuantity() < productStock.getProductChangeQuantity()) {
				response.put("success", false);
				response.put("message", "재고가 부족합니다.");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			int result = stockService.sellProductStock(productStock);
			if (result > 0) {
				response.put("success", true);
				response.put("message", "판매가 완료되었습니다.");
				return ResponseEntity.ok(response);
			} else {
				response.put("success", false);
				response.put("message", "판매 처리에 실패했습니다.");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "오류가 발생했습니다: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	// 재고 폐기
	@PostMapping("/disposal/{productId}")
	public ResponseEntity<Map<String, Object>> disposalStock(@PathVariable Long productId) {
		Map<String, Object> response = new HashMap<>();
		try {
			// 현재 재고 확인
			ProductStock currentStock = stockService.getCurrentStock(productId);
			if (currentStock == null || currentStock.getProductQuantity() <= 0) {
				response.put("success", false);
				response.put("message", "재고가 없습니다.");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			int result = stockService.disposalProductStock(productId);
			if (result > 0) {
				response.put("success", true);
				response.put("message", "재고가 폐기되었습니다.");
				return ResponseEntity.ok(response);
			} else {
				response.put("success", false);
				response.put("message", "재고 폐기에 실패했습니다.");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "오류가 발생했습니다: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	// 재고 변화 이력 전체 조회
	@GetMapping("/history")
	public ResponseEntity<List<ProductStock>> getAllStockHistory() {
		try {
			List<ProductStock> history = stockService.getAllStockHistory();
			return ResponseEntity.ok(history);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// 특정 상품 재고 변화 이력 조회
	@GetMapping("/history/{productId}")
	public ResponseEntity<List<ProductStock>> getStockHistoryByProduct(@PathVariable Long productId) {
		try {
			List<ProductStock> history = stockService.getStockHistoryByProduct(productId);
			return ResponseEntity.ok(history);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// 현재 상품별 재고 조회
	@GetMapping("/current/{productId}")
	public ResponseEntity<ProductStock> getCurrentStock(@PathVariable Long productId) {
		try {
			ProductStock stock = stockService.getCurrentStock(productId);
			if (stock != null) {
				return ResponseEntity.ok(stock);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
