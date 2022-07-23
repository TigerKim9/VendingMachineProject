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
public class Data {

//	private String id;
	
	private String email;
	
	private String first_name;
	
	private String last_name;
	
	private String avatar;
	
	
}
