package com.vendingMachine.vendingMachine.controller;

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

import com.vendingMachine.service.VendingMachineService;
import com.vendingMachine.vendingMachines.VendingMachine;

@RestController
@RequestMapping("/api/vending")
public class VendingMachineAPIController {

	@Autowired
	private VendingMachineService vendingMachineService;

	@PostMapping("/register")
	public ResponseEntity<Map<String, Object>> registerVendingMachine(@RequestBody VendingMachine vendingMachine) {
		Map<String, Object> response = new HashMap<>();
		try {
			int result = vendingMachineService.addVendingMachine(vendingMachine);
			response.put("success", result > 0);
			response.put("message", result > 0 ? "자판기가 등록되었습니다." : "자판기 등록에 실패했습니다.");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "오류가 발생했습니다: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@PutMapping("/update")
	public ResponseEntity<Map<String, Object>> updateVendingMachine(@RequestBody VendingMachine vendingMachine) {
		Map<String, Object> response = new HashMap<>();
		try {
			int result = vendingMachineService.updateVendingMachine(vendingMachine);
			response.put("success", result > 0);
			response.put("message", result > 0 ? "자판기 정보가 수정되었습니다." : "수정에 실패했습니다.");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "오류가 발생했습니다: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Map<String, Object>> deleteVendingMachine(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			int result = vendingMachineService.deleteVendingMachine(id);
			response.put("success", result > 0);
			response.put("message", result > 0 ? "자판기가 삭제되었습니다." : "삭제에 실패했습니다.");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "오류가 발생했습니다: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@GetMapping("/list")
	public ResponseEntity<List<VendingMachine>> getAllVendingMachines() {
		return ResponseEntity.ok(vendingMachineService.getAllVendingMachines());
	}

	@GetMapping("/{id}")
	public ResponseEntity<VendingMachine> getVendingMachine(@PathVariable Long id) {
		VendingMachine vm = vendingMachineService.getVendingMachineById(id);
		return vm != null ? ResponseEntity.ok(vm) : ResponseEntity.notFound().build();
	}
}
