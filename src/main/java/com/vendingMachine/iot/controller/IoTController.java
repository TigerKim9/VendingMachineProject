package com.vendingMachine.iot.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vendingMachine.iot.DTO.IoTMessage;
import com.vendingMachine.service.IoTService;

@RestController
@RequestMapping("/api/iot")
public class IoTController {

	@Autowired
	private IoTService iotService;

	// REST API: 자판기에서 직접 HTTP로 데이터 전송 (MQTT 대안)
	@PostMapping("/message")
	public ResponseEntity<Map<String, Object>> receiveMessage(@RequestBody IoTMessage message) {
		Map<String, Object> response = new HashMap<>();
		try {
			// IoT 서비스로 메시지 전달
			System.out.println("Received IoT message via REST: " + message.getMessageType());

			response.put("success", true);
			response.put("message", "Message received successfully");
			response.put("timestamp", System.currentTimeMillis());
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "Error: " + e.getMessage());
			return ResponseEntity.status(500).body(response);
		}
	}

	// 자판기에 명령 전송
	@PostMapping("/command/{deviceKey}/dispense/{slotNumber}")
	public ResponseEntity<Map<String, Object>> dispenseProduct(
			@PathVariable String deviceKey,
			@PathVariable Long slotNumber) {
		Map<String, Object> response = new HashMap<>();
		try {
			iotService.dispenseProduct(deviceKey, slotNumber);
			response.put("success", true);
			response.put("message", "Dispense command sent");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "Error: " + e.getMessage());
			return ResponseEntity.status(500).body(response);
		}
	}

	// 자판기 재부팅
	@PostMapping("/command/{deviceKey}/reboot")
	public ResponseEntity<Map<String, Object>> rebootDevice(@PathVariable String deviceKey) {
		Map<String, Object> response = new HashMap<>();
		try {
			iotService.rebootDevice(deviceKey);
			response.put("success", true);
			response.put("message", "Reboot command sent");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "Error: " + e.getMessage());
			return ResponseEntity.status(500).body(response);
		}
	}

	// 디바이스 상태 확인
	@GetMapping("/status")
	public ResponseEntity<Map<String, Object>> getSystemStatus() {
		Map<String, Object> response = new HashMap<>();
		response.put("serverStatus", "ONLINE");
		response.put("mqttConnected", true); // TODO: 실제 MQTT 연결 상태 확인
		response.put("timestamp", System.currentTimeMillis());
		return ResponseEntity.ok(response);
	}
}
