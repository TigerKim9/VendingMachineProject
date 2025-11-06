package com.vendingMachine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vendingMachine.Mapper.VendingMachineMapper;
import com.vendingMachine.vendingMachines.VendingMachine;

@Service
public class VendingMachineService {

	@Autowired
	private VendingMachineMapper vendingMachineMapper;

	@Transactional
	public int addVendingMachine(VendingMachine vendingMachine) {
		return vendingMachineMapper.addVendingMachine(vendingMachine);
	}

	@Transactional
	public int updateVendingMachine(VendingMachine vendingMachine) {
		return vendingMachineMapper.updateVendingMachine(vendingMachine);
	}

	@Transactional
	public int deleteVendingMachine(Long vendingMachineId) {
		return vendingMachineMapper.deleteVendingMachine(vendingMachineId);
	}

	public VendingMachine getVendingMachineById(Long vendingMachineId) {
		return vendingMachineMapper.getVendingMachineById(vendingMachineId);
	}

	public List<VendingMachine> getAllVendingMachines() {
		return vendingMachineMapper.getAllVendingMachines();
	}

	public List<VendingMachine> getVendingMachinesByLocation(String location) {
		return vendingMachineMapper.getVendingMachinesByLocation(location);
	}
}
