package com.vendingMachine.home.DTO;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//유저 Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class User {

	//회원 고유번호
	private Long userId;
	
	//회원아이디
	private String userLoginId;
	
	//회원 비밀번호
	private String passWord;
	
	//회원 이름
	private String userName;
	
	//회원 이메일
	private String userEmail;
	
	//회원 전화번호
	private String userPhone;
	
	//본인인증 여부
	private boolean kyc;
}
