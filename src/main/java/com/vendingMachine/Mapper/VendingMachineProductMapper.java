package com.vendingMachine.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vendingMachine.vendingMachines.VendingMachineProduct;

@Mapper
public interface VendingMachineProductMapper {

	// 자판기에 상품 배치
	int placeProduct(VendingMachineProduct vmProduct);

	// 배치된 상품 수정
	int updatePlacement(VendingMachineProduct vmProduct);

	// 배치된 상품 제거
	int removePlacement(Long vmProductId);

	// 자판기의 모든 배치 상품 조회
	List<VendingMachineProduct> getProductsByVendingMachine(Long vendingMachineId);

	// 특정 배치 조회
	VendingMachineProduct getPlacementById(Long vmProductId);

	// 자판기의 특정 슬롯 조회
	VendingMachineProduct getProductBySlot(Long vendingMachineId, Integer slotNumber);

	// 모든 배치 조회
	List<VendingMachineProduct> getAllPlacements();

	// 재고 부족 알림 목록
	List<VendingMachineProduct> getLowStockProducts();
}
