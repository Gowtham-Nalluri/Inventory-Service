package com.hcl.inventory.entity;

import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "inventories")
public class Inventory {

    @Id
    private String id;

    private String name;

    private String category;

    private String subCategory;

    private LocalDate manufacturingDate;

    private LocalDate expiryDate;

    private String specification;

    private BigDecimal price;

    private Integer stock;

    private String model;

    private String seller;

    private String location;
}