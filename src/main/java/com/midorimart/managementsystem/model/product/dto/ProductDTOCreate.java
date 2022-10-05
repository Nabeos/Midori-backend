package com.midorimart.managementsystem.model.product.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductDTOCreate {
    private int category;
    private String title;
    private double price;
    private String thumbnails;
    private String description;
}
