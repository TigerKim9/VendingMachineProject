package com.vendingMachine.vendingMachines;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendingMachine {

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
}
