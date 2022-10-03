package com.vendingMachine.service;

import javax.annotation.Resource;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vendingMachine.Mapper.HomeMapper;
import com.vendingMachine.home.DTO.User;

/*
 * 1.회원가입
 * 2.회원정보변경
 * 3.문의하기
 * 
 */


@Service
public class HomeService {

	@Resource
	HomeMapper homeMapper;
	
	PasswordEncoder passwordEncoder;
	
	//회원가입
	@Transactional
	public int registUser(User user) {
		user.setPassWord(passwordEncoder.encode(user.getPassWord()));
		int result = homeMapper.registUser(user);
		return result;
	}
	
	//비밀번호 일치시에 회원정보 변경
	@Transactional
	public int changeUserInfo(User user) {
		
		String rawPw = user.getPassWord();
		String encodedPw = homeMapper.encodedPw(user.getUserId());
		boolean matchPw = passwordEncoder.matches(rawPw, encodedPw);
		
		if(matchPw) {
			int result = homeMapper.changeUserInfo(user);
			return result;
			
		}else {
			return 0;
		}
		
	}
	
	//문의하기
	public int injury(Injury injury) {
		
		int result = homeMapper.injury(injury);
		return result;
		
	}
}
