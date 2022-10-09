package com.vendingMachine.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vendingMachine.home.DTO.User;

@Mapper
public interface UserMapper {

	//중복아이디 체크
	int idCheck(String userId);
	
	// 사용자 추가
	int addUser(User user);
	
	// 사용자 권한 추가
	int addAuth(Long userId, String auth);
	
	// 사용자 삭제
	int deleteUser(User user);
	
	// 특정 사용자 권한 삭제
	int deleteAuth(String id, String auth);
	
	// 특정 사용자 권한(들) 전부 삭제
	int deleteAuths(Long userId);
	
	// 특정 id (username) 의 사용자 찾기
	User findById(String id);
	
	// 특정 id (username) 의 권한(들) 뽑기
	List<String> selectAuthoritiesById(Long userId);
	
	//아이디 검색기록
	int searchLog(String userId);
	
} // end DAO








