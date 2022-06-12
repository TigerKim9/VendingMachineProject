package com.vendingMachine.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vendingMachine.home.DTO.User;

@Mapper
public interface AdminHomeMapper {


	//회원상세
	User userInfo(long userId);
	
	//유저 리스트
	List<User> userList();
	
	//유저 정보 변경
	int changeUserInfo(User user);
	
}
