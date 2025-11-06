package com.vendingMachine.inquiry.DTO;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Inquiry {
	private Long inquiryId;
	private Long userId;
	private String title;
	private String content;
	private String status; // 대기, 답변완료
	private String answer;
	private LocalDateTime createdDate;
	private LocalDateTime answeredDate;
	private String userName;
}
