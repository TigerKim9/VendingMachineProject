package com.vendingMachine.home.DTO;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//유저 Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class WebClientBean {

	private String page;
	
	private String per_page;
	
	private String total;
	
	private String total_pages;
	
	private List<Data> data;
	
}
