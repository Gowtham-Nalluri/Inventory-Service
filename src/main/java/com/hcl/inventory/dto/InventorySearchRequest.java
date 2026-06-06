package com.hcl.inventory.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Inventory Search Request")
public class InventorySearchRequest {

    private String name;
    private String category;
    private String subCategory;
    private String seller;
    private String location;
    private String model;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Integer minStock;
    private Integer maxStock;
    private LocalDate manufacturingDate;
    private LocalDate expiryDate;
    private Pagination pagination;
    private SortingRequest sorting;
}