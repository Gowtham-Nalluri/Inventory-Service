package com.hcl.inventory.repository;
import com.hcl.inventory.dto.InventorySearchRequest;
import com.hcl.inventory.entity.Inventory;
import org.springframework.data.domain.Page;

public interface InventoryRepositoryCustom{
    Page<Inventory> searchInventory(InventorySearchRequest req);
}