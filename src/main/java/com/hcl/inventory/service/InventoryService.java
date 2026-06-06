package com.hcl.inventory.service;
import com.hcl.inventory.dto.*;
import com.hcl.inventory.entity.*;
import com.hcl.inventory.repository.*;
import com.hcl.inventory.validator.InventorySearchValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService{

    private final InventoryRepository repository;
    private final InventorySearchValidator validator;

    public Page<InventoryResponse> searchInventory(InventorySearchRequest request) {

        validator.validate(request);

        return repository
                .searchInventory(request)
                .map(this::convertToResponse);
    }

    private InventoryResponse convertToResponse(
            Inventory inventory) {

        return new InventoryResponse(
                inventory.getId(),
                inventory.getName(),
                inventory.getCategory(),
                inventory.getSubCategory(),
                inventory.getPrice(),
                inventory.getStock(),
                inventory.getSeller(),
                inventory.getLocation(),
                inventory.getModel()
        );
    }
}