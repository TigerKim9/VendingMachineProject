package com.vendingMachine.Mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.vendingMachine.product.DTO.SellProductHistory;

@Mapper
public interface SalesMapper {
	List<SellProductHistory> getAllSalesHistory();
	List<SellProductHistory> getSalesHistoryByProduct(Long productId);
	List<SellProductHistory> getSalesHistoryByVendingMachine(Long vendingMachineId);
}
