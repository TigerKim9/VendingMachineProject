package com.vendingMachine.Mapper;

import org.apache.ibatis.annotations.Mapper;

import com.vendingMachine.home.DTO.User;

@Mapper
public interface HomeMapper {

	//회원가입
	int registUser(User user);
	
	//마이페이지
	User myPage(long userId);
	
	int changeUserInfo(User user);
	
	String encodedPw(Long userId);
}
