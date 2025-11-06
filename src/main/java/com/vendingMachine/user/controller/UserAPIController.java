package com.vendingMachine.user.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vendingMachine.config.auth.PrincipalDetails;
import com.vendingMachine.home.DTO.User;
import com.vendingMachine.service.HomeService;

@RestController
@RequestMapping("/api/user")
public class UserAPIController {

	@Autowired
	private HomeService homeService;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@PostMapping("/update")
	public ResponseEntity<Map<String, Object>> updateUser(@RequestBody User user,
			@AuthenticationPrincipal PrincipalDetails principal) {
		Map<String, Object> response = new HashMap<>();
		try {
			if (principal == null) {
				response.put("success", false);
				response.put("message", "로그인이 필요합니다.");
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}

			user.setUserId(principal.getUser().getUid());
			int result = homeService.changeUserInfo(user);
			response.put("success", result > 0);
			response.put("message", result > 0 ? "회원 정보가 수정되었습니다." : "수정에 실패했습니다.");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "오류가 발생했습니다: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@PostMapping("/changePassword")
	public ResponseEntity<Map<String, Object>> changePassword(@RequestBody Map<String, String> passwords,
			@AuthenticationPrincipal PrincipalDetails principal) {
		Map<String, Object> response = new HashMap<>();
		try {
			if (principal == null) {
				response.put("success", false);
				response.put("message", "로그인이 필요합니다.");
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}

			String currentPassword = passwords.get("currentPassword");
			String newPassword = passwords.get("newPassword");

			// 현재 비밀번호 확인
			String encodedPw = homeService.encodedPw(principal.getUser().getUid());
			if (!encoder.matches(currentPassword, encodedPw)) {
				response.put("success", false);
				response.put("message", "현재 비밀번호가 일치하지 않습니다.");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			// 새 비밀번호로 변경
			User user = new User();
			user.setUserId(principal.getUser().getUid());
			user.setPassWord(encoder.encode(newPassword));

			int result = homeService.changeUserInfo(user);
			response.put("success", result > 0);
			response.put("message", result > 0 ? "비밀번호가 변경되었습니다." : "변경에 실패했습니다.");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "오류가 발생했습니다: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
}
