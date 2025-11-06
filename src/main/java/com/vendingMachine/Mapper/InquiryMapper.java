package com.vendingMachine.Mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.vendingMachine.inquiry.DTO.Inquiry;

@Mapper
public interface InquiryMapper {
	int addInquiry(Inquiry inquiry);
	List<Inquiry> getAllInquiries();
	List<Inquiry> getInquiriesByUser(Long userId);
	Inquiry getInquiryById(Long inquiryId);
	int updateAnswer(Inquiry inquiry);
}
