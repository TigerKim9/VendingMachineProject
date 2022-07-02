package com.vendingMachine.vendingMachines;

import java.util.List;

import com.vendingMachine.product.DTO.Product;

import lombok.Getter;
import lombok.Setter;

//자판기 Entity
@Getter
@Setter
public class VendingMachineDTO {

	//자판기 고유 아이디
	private long vendingMachineId;
	
	//자판기 이름
	private String vendingMachineName;
	
	//자판기 위치
	private String vendingMachineLocation;
	
	//자판기 모델
	private String vendingMachineModel;
	
	//자판기 상품 칸 수
	private short vendingMachineSlot;
	
	private List<Product> vendingMachinesProduct;
}
