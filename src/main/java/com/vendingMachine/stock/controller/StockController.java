package com.vendingMachine.stock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vendingMachine.service.ProductService;
import com.vendingMachine.service.ProductStockService;

@Controller
@RequestMapping("/stock")
public class StockController {

	@Autowired
	private ProductStockService stockService;

	@Autowired
	private ProductService productService;

	// 재고 관리 메인 페이지
	@GetMapping("/manage")
	public String stockManage(Model model) {
		model.addAttribute("products", productService.getAllProducts());
		model.addAttribute("stockHistory", stockService.getAllStockHistory());
		return "stock/stockManage";
	}

	// 재고 추가 페이지
	@GetMapping("/add")
	public String stockAddForm(Model model) {
		model.addAttribute("products", productService.getAllProducts());
		return "stock/stockAdd";
	}

	// 재고 판매/폐기 페이지
	@GetMapping("/reduce")
	public String stockReduceForm(Model model) {
		model.addAttribute("products", productService.getAllProducts());
		return "stock/stockReduce";
	}

	// 특정 상품 재고 이력 페이지
	@GetMapping("/history/{productId}")
	public String stockHistory(@PathVariable Long productId, Model model) {
		model.addAttribute("product", productService.getProductById(productId));
		model.addAttribute("stockHistory", stockService.getStockHistoryByProduct(productId));
		model.addAttribute("currentStock", stockService.getCurrentStock(productId));
		return "stock/stockHistory";
	}
}
