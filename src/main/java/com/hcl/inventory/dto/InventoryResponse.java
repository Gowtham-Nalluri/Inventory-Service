package com.hcl.inventory.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Inventory Search Response")
public record InventoryResponse(

        String id,

        String name,

        String category,

        String subCategory,

        BigDecimal price,

        Integer stock,

        String seller,

        String location,

        String model

) {
}