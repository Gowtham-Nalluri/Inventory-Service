package com.hcl.inventory.repository;
import com.hcl.inventory.entity.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InventoryRepository extends MongoRepository<Inventory,String>,
        InventoryRepositoryCustom{

}