package com.vendingMachine.product.DTO;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {

	//카테고리 ID
	private Long CategoryId;
	
	//카테고리 이름
	private String CategoryName;
	
	//카테고리에 속한 제품목록
	private List<Product> producctInCategory;
	
	
}
