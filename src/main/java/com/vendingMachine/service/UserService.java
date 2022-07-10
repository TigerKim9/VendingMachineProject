package com.vendingMachine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vendingMachine.Mapper.UserMapper;
import com.vendingMachine.home.DTO.User;

@Service
public class UserService {
	
	@Autowired
	UserMapper dao;
	
	public UserService() {
		System.out.println("UserService() 생성");
	}
	
	// 회원가입
	// ROLE_MEMBER 권한 부여
	@Transactional
	public int addMember(User user) {
		int cnt = dao.addUser(user);
		dao.addAuth(user.getUserId(), "ROLE_MEMBER");
		return cnt;
	}
	
	// 회원삭제
	@Transactional
	public int deleteMember(User user) {
		dao.deleteAuths(user.getUserId());  // 권한(들) 먼저 삭제
		int cnt = dao.deleteUser(user);
		return cnt;
	}
	
	// 특정 id(username) 의 정보 가져오기
	public User findById(String id) {
		return dao.findById(id);
	}
	
	// 특정 id 의 권한(들) 정보 가져오기
	public List<String> selectAuthoritiesById(Long userId){
		return dao.selectAuthoritiesById(userId);
	}
}




















