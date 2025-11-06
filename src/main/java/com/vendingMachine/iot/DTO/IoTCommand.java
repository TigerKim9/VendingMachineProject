package com.vendingMachine.iot.DTO;

import lombok.Getter;
import lombok.Setter;

// 서버에서 자판기로 전송하는 명령
@Getter
@Setter
public class IoTCommand {
	private String commandType; // DISPENSE, UPDATE_PRICE, REBOOT, UPDATE_FIRMWARE
	private Object payload;
	private Long timestamp;
}
