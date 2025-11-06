package com.vendingMachine.vmProduct.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vendingMachine.service.ProductService;
import com.vendingMachine.service.VendingMachineProductService;
import com.vendingMachine.service.VendingMachineService;

@Controller
@RequestMapping("/vmproduct")
public class VMProductController {

	@Autowired
	private VendingMachineProductService vmProductService;

	@Autowired
	private VendingMachineService vendingMachineService;

	@Autowired
	private ProductService productService;

	// 자판기 상품 배치 관리 메인
	@GetMapping("/manage")
	public String vmProductManage(Model model) {
		model.addAttribute("placements", vmProductService.getAllPlacements());
		model.addAttribute("lowStockProducts", vmProductService.getLowStockProducts());
		return "vmproduct/vmProductManage";
	}

	// 자판기별 상품 배치 현황
	@GetMapping("/vending/{vmId}")
	public String vendingMachineProducts(@PathVariable Long vmId, Model model) {
		model.addAttribute("vendingMachine", vendingMachineService.getVendingMachineById(vmId));
		model.addAttribute("placements", vmProductService.getProductsByVendingMachine(vmId));
		return "vmproduct/vendingProducts";
	}

	// 상품 배치 등록 페이지
	@GetMapping("/place")
	public String placeProductForm(Model model) {
		model.addAttribute("vendingMachines", vendingMachineService.getAllVendingMachines());
		model.addAttribute("products", productService.getAllProducts());
		return "vmproduct/placeProduct";
	}

	// 상품 배치 수정 페이지
	@GetMapping("/edit/{id}")
	public String editPlacementForm(@PathVariable Long id, Model model) {
		model.addAttribute("placement", vmProductService.getPlacementById(id));
		model.addAttribute("products", productService.getAllProducts());
		return "vmproduct/editPlacement";
	}
}
