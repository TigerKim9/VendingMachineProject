package com.vendingMachine.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.vendingMachine.Mapper.UserMapper;
import com.vendingMachine.home.DTO.User;
import com.vendingMachine.home.DTO.WebClientBean;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserMapper userDao;
	
	
	
	
	//중복아이디 체크
	public int idCheck(String userId) {
		int cnt = userDao.idCheck(userId);
		return cnt;
	}
	
	// 회원가입
	// ROLE_MEMBER 권한 부여
	@Transactional
	public int addMember(User user) {
		int cnt = userDao.addUser(user);
		userDao.addAuth(user.getUserId(), "ROLE_MEMBER");
		
		
		return cnt;
	}
	
	// 회원삭제
	@Transactional
	public int deleteMember(User user) {
		userDao.deleteAuths(user.getUserId());  // 권한(들) 먼저 삭제
		int cnt = userDao.deleteUser(user);
		return cnt;
	}
	
	// 특정 id(username) 의 정보 가져오기
	public User findById(String id) {
		return userDao.findById(id);
	}
	
	// 특정 id 의 권한(들) 정보 가져오기
	public List<String> selectAuthoritiesById(Long userId){
		return userDao.selectAuthoritiesById(userId);
	}
}




















