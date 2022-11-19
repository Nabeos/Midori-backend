package com.midorimart.managementsystem.model.product.dto;

import java.util.Date;
import java.util.List;

import com.midorimart.managementsystem.model.category.dto.CategoryDTOResponse;
import com.midorimart.managementsystem.model.merchant.dto.MerchantDTOResponse;
import com.midorimart.managementsystem.model.productQuantityInStock.dto.ProductQuantityDTOResponse;
import com.midorimart.managementsystem.model.productUnit.dto.ProductUnitDTOResponse;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductDTOResponse {
    private int id;
    private String slug;
    private String sku;
    private CategoryDTOResponse category;
    private String title;
    private double price;
    private int discount;
    private int quantity;
    private List<String> thumbnails;
    private String description;
    private String status;
    private Date created_at;
    private Date updated_at;
    private int deleted;
    private double amount;
    private ProductUnitDTOResponse unit;
    private MerchantDTOResponse merchant;
    private double star;
    private List<ProductQuantityDTOResponse> productQuantityInStock;
}
