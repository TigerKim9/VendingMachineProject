package com.vendingMachine.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vendingMachine.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	// 상품 목록 페이지
	@GetMapping("/list")
	public String productList(Model model) {
		model.addAttribute("products", productService.getAllProducts());
		model.addAttribute("categories", productService.getAllCategories());
		return "product/productList";
	}

	// 상품 등록 페이지
	@GetMapping("/register")
	public String productRegisterForm(Model model) {
		model.addAttribute("categories", productService.getAllCategories());
		return "product/productRegister";
	}

	// 상품 수정 페이지
	@GetMapping("/edit/{productId}")
	public String productEditForm(@PathVariable Long productId, Model model) {
		model.addAttribute("product", productService.getProductById(productId));
		model.addAttribute("categories", productService.getAllCategories());
		return "product/productEdit";
	}

	// 카테고리 관리 페이지
	@GetMapping("/category")
	public String categoryManage(Model model) {
		model.addAttribute("categories", productService.getAllCategories());
		return "product/categoryManage";
	}
}
