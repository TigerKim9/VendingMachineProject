package com.vendingMachine.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vendingMachine.Mapper.SalesMapper;
import com.vendingMachine.product.DTO.SellProductHistory;

@Service
public class SalesService {
	@Autowired
	private SalesMapper salesMapper;

	public List<SellProductHistory> getAllSalesHistory() {
		return salesMapper.getAllSalesHistory();
	}

	public List<SellProductHistory> getSalesHistoryByProduct(Long productId) {
		return salesMapper.getSalesHistoryByProduct(productId);
	}

	public List<SellProductHistory> getSalesHistoryByVendingMachine(Long vendingMachineId) {
		return salesMapper.getSalesHistoryByVendingMachine(vendingMachineId);
	}
}
