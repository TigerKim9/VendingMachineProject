package com.vendingMachine.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vendingMachine.vendingMachines.VendingMachine;

@Mapper
public interface VendingMachineMapper {

	// 자판기 등록
	int addVendingMachine(VendingMachine vendingMachine);

	// 자판기 수정
	int updateVendingMachine(VendingMachine vendingMachine);

	// 자판기 삭제
	int deleteVendingMachine(Long vendingMachineId);

	// 자판기 단건 조회
	VendingMachine getVendingMachineById(Long vendingMachineId);

	// 자판기 전체 조회
	List<VendingMachine> getAllVendingMachines();

	// 위치별 자판기 조회
	List<VendingMachine> getVendingMachinesByLocation(String location);
}
