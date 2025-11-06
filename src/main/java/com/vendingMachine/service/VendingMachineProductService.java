package com.vendingMachine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vendingMachine.Mapper.VendingMachineProductMapper;
import com.vendingMachine.vendingMachines.VendingMachineProduct;

@Service
public class VendingMachineProductService {

	@Autowired
	private VendingMachineProductMapper vmProductMapper;

	@Transactional
	public int placeProduct(VendingMachineProduct vmProduct) {
		return vmProductMapper.placeProduct(vmProduct);
	}

	@Transactional
	public int updatePlacement(VendingMachineProduct vmProduct) {
		return vmProductMapper.updatePlacement(vmProduct);
	}

	@Transactional
	public int removePlacement(Long vmProductId) {
		return vmProductMapper.removePlacement(vmProductId);
	}

	public List<VendingMachineProduct> getProductsByVendingMachine(Long vendingMachineId) {
		return vmProductMapper.getProductsByVendingMachine(vendingMachineId);
	}

	public VendingMachineProduct getPlacementById(Long vmProductId) {
		return vmProductMapper.getPlacementById(vmProductId);
	}

	public VendingMachineProduct getProductBySlot(Long vendingMachineId, Integer slotNumber) {
		return vmProductMapper.getProductBySlot(vendingMachineId, slotNumber);
	}

	public List<VendingMachineProduct> getAllPlacements() {
		return vmProductMapper.getAllPlacements();
	}

	public List<VendingMachineProduct> getLowStockProducts() {
		return vmProductMapper.getLowStockProducts();
	}
}
