package com.vendingMachine.vmProduct.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.vendingMachine.service.VendingMachineProductService;
import com.vendingMachine.vendingMachines.VendingMachineProduct;

@RestController
@RequestMapping("/api/vmproduct")
public class VMProductAPIController {

	@Autowired
	private VendingMachineProductService vmProductService;

	@PostMapping("/place")
	public ResponseEntity<Map<String, Object>> placeProduct(@RequestBody VendingMachineProduct vmProduct) {
		Map<String, Object> response = new HashMap<>();
		try {
			// 해당 슬롯에 이미 상품이 있는지 확인
			VendingMachineProduct existing = vmProductService.getProductBySlot(
				vmProduct.getVendingMachineId(), vmProduct.getSlotNumber());

			if (existing != null) {
				response.put("success", false);
				response.put("message", "해당 슬롯에 이미 상품이 배치되어 있습니다.");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			int result = vmProductService.placeProduct(vmProduct);
			response.put("success", result > 0);
			response.put("message", result > 0 ? "상품이 배치되었습니다." : "배치에 실패했습니다.");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "오류가 발생했습니다: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@PutMapping("/update")
	public ResponseEntity<Map<String, Object>> updatePlacement(@RequestBody VendingMachineProduct vmProduct) {
		Map<String, Object> response = new HashMap<>();
		try {
			int result = vmProductService.updatePlacement(vmProduct);
			response.put("success", result > 0);
			response.put("message", result > 0 ? "배치 정보가 수정되었습니다." : "수정에 실패했습니다.");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "오류가 발생했습니다: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@DeleteMapping("/remove/{id}")
	public ResponseEntity<Map<String, Object>> removePlacement(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			int result = vmProductService.removePlacement(id);
			response.put("success", result > 0);
			response.put("message", result > 0 ? "배치가 제거되었습니다." : "제거에 실패했습니다.");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "오류가 발생했습니다: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@GetMapping("/vending/{vmId}")
	public ResponseEntity<List<VendingMachineProduct>> getProductsByVendingMachine(@PathVariable Long vmId) {
		return ResponseEntity.ok(vmProductService.getProductsByVendingMachine(vmId));
	}

	@GetMapping("/lowstock")
	public ResponseEntity<List<VendingMachineProduct>> getLowStockProducts() {
		return ResponseEntity.ok(vmProductService.getLowStockProducts());
	}
}
