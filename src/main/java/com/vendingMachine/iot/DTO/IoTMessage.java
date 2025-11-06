package com.vendingMachine.iot.DTO;

import lombok.Getter;
import lombok.Setter;

// 자판기에서 서버로 전송하는 데이터
@Getter
@Setter
public class IoTMessage {
	private String deviceKey;
	private String messageType; // HEARTBEAT, SALE, STOCK_CHANGE, ERROR, STATUS
	private Object payload; // JSON 데이터
	private Long timestamp;
}
