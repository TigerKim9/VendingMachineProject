package com.vendingMachine.home.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vendingMachine.home.DTO.User;
import com.vendingMachine.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LoginAPIController {
	
	private final UserService userService;
	
	Logger logger = LoggerFactory.getLogger(LoginAPIController.class);
	
	//문자인증
	@PostMapping("/smsCertif")
	public int smsCertif(String phoneNum) {
//		 Message message = new Message();
	        // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
//	        message.setFrom("01012345678");
//	        message.setTo(registUserDto.getMemPhone());
//	        
	        //난수 4자리 발생
	        String certificateNum ="";
	        for(int i =0; i < 4;i++) {
	        	certificateNum += (String.valueOf(Math.round(Math.random() * 10)));
	        }
	        logger.info("인증번호 : {}", certificateNum);
//	        registUserDto.setCertificateNum(certificateNum);
//	        //인증번호 저장
//	        userService.insertCertificate(registUserDto);
//	        message.setText("[밴딩] 인증번호는 ["+certificateNum +"] 입니다. 정확히 입력해주세요.");
	        
//	        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
//	        System.out.println(response);
//
//	        return response;
	        return 1;
		
	}
	//회원가입
	@PostMapping("/registUser")
	public int registUser(@RequestBody User user) {
		int result = userService.addMember(user);
		return result;
	}
	
	//중복아이디 체크
	@PostMapping("/idCheck")
	public int idCheck(@RequestBody String userId) {
		int result = userService.idCheck(userId);
		logger.info("중복아이디 개수 : {}", result);
		return result;
	}
	

}
