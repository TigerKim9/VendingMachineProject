package com.vendingMachine.Mapper;

import org.apache.ibatis.annotations.Mapper;

import com.vendingMachine.home.DTO.User;

@Mapper
public interface HomeMapper {

	//회원가입
	int registUser(User user);
	
	//마이페이지
	User myPage(long userId);
	
	//회원정보 변경
	int changeUserInfo(User user);
	
	//비밀번호 확인용 비밀번호 불러오기
	String encodedPw(Long userId);
}
