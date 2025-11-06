package com.vendingMachine.iot.DTO;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

// IoT 자판기 디바이스 정보
@Getter
@Setter
public class VendingMachineDevice {
	private Long deviceId;
	private Long vendingMachineId;
	private String deviceKey; // 디바이스 고유 키 (인증용)
	private String ipAddress;
	private String status; // ONLINE, OFFLINE, ERROR
	private LocalDateTime lastHeartbeat; // 마지막 통신 시간
	private String firmwareVersion;
	private Float temperature; // 온도
	private Integer errorCode; // 에러 코드
	private String errorMessage; // 에러 메시지
	private LocalDateTime registeredDate;
}
