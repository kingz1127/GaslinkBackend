package com.gaslink.api.modules.inventory;
import com.gaslink.api.modules.inventory.dto.*;
import com.gaslink.api.shared.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public List<InventoryDto> getByVendor(UUID vendorId) {
        return inventoryRepository.findByVendorId(vendorId).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional
    public InventoryDto upsert(UUID vendorId, UpdateInventoryRequest req) {
        CylinderInventory inv = inventoryRepository.findByVendorIdAndSizeKg(vendorId, req.getSizeKg())
                .orElse(CylinderInventory.builder().vendorId(vendorId).sizeKg(req.getSizeKg()).build());
        inv.setAvailableQty(req.getAvailableQty());
        if (req.getRefillPrice() != null) inv.setRefillPrice(req.getRefillPrice());
        if (req.getExchangePrice() != null) inv.setExchangePrice(req.getExchangePrice());
        if (req.getRentalPricePerDay() != null) inv.setRentalPricePerDay(req.getRentalPricePerDay());
        if (req.getPurchasePrice() != null) inv.setPurchasePrice(req.getPurchasePrice());
        return toDto(inventoryRepository.save(inv));
    }

    @Transactional
    public void delete(UUID vendorId, UUID inventoryId) {
        CylinderInventory inv = inventoryRepository.findById(inventoryId).orElseThrow(() -> new ResourceNotFoundException("Inventory item not found"));
        if (!inv.getVendorId().equals(vendorId)) throw new ForbiddenException("Access denied");
        inventoryRepository.delete(inv);
    }

    private InventoryDto toDto(CylinderInventory i) {
        return InventoryDto.builder().id(i.getId()).vendorId(i.getVendorId()).sizeKg(i.getSizeKg())
                .availableQty(i.getAvailableQty()).refillPrice(i.getRefillPrice()).exchangePrice(i.getExchangePrice())
                .rentalPricePerDay(i.getRentalPricePerDay()).purchasePrice(i.getPurchasePrice()).build();
    }
}