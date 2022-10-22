package com.midorimart.managementsystem.model.product.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductDTOCreate {
    private String slug;
    private String sku;
    private int category;
    private String title;
    private double price;
    private double amount;
    private String description;
}
