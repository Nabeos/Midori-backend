package com.midorimart.managementsystem.model.product.dto;

import java.util.List;

import com.midorimart.managementsystem.model.category.dto.CategoryDTOResponse;
import com.midorimart.managementsystem.model.comment.dto.CommentDTOResponse;
import com.midorimart.managementsystem.model.country.dto.CountryDTOResponse;
import com.midorimart.managementsystem.model.merchant.dto.MerchantDTOResponse;
import com.midorimart.managementsystem.model.productQuantityInStock.dto.ProductQuantityDTOResponse;
import com.midorimart.managementsystem.model.productUnit.dto.ProductUnitDTOResponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDetailDTOResponse{
    private int id;
    private String slug;
    private String sku;
    private CategoryDTOResponse category;
    private String title;
    private double price;
    private int discount;
    private CountryDTOResponse origin;
    private List<String> thumbnails;
    private String description;
    private String status;
    private String created_at;
    private String updated_at;
    private int deleted;
    private double amount;
    private int quantity;
    private double star;
    private MerchantDTOResponse merchant;
    private ProductUnitDTOResponse unit;
    private List<ProductQuantityDTOResponse> expiryDate;
    private List<CommentDTOResponse> comments;
}
