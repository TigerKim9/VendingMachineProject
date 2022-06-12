package com.vendingMachine.home.DTO;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class UserHistory {
	
	//회원번호
	private Long userId;

	// 이름 변경 기록
	private String nameChange;

	//핸드폰번호 변경 기록
	private String phoneChange;
	
	//변경날짜
	private LocalDateTime changeTime;


}
