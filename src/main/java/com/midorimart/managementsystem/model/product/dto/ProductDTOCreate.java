package com.midorimart.managementsystem.model.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTOCreate {
    private int category;
    private int discount;
    private String title;
    private double price;
    private double amount;
    private String description;
    private String origin;
    private int productUnit;
}
