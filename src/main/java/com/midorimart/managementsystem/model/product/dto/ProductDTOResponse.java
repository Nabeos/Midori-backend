package com.midorimart.managementsystem.model.product.dto;

import java.util.Date;

import com.midorimart.managementsystem.model.category.dto.CategoryDTOResponse;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductDTOResponse {
    private int id;
    private CategoryDTOResponse category;
    private String title;
    private double price;
    private int discount;
    private String thumbnails;
    private String description;
    private String status;
    private Date created_at;
    private Date updated_at;
    private int deleted;
}
