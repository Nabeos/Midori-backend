package com.midorimart.managementsystem.model.product.dto;

import com.midorimart.managementsystem.entity.Category;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductDTOCreate {
    private Category category;
    private String title;
    private double price;
    private String thumbnails;
    private String description;
}
