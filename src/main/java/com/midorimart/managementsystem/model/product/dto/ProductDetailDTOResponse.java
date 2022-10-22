package com.midorimart.managementsystem.model.product.dto;

import java.util.Date;
import java.util.List;

import com.midorimart.managementsystem.model.category.dto.CategoryDTOResponse;
import com.midorimart.managementsystem.model.comment.dto.CommentDTOResponse;
import com.midorimart.managementsystem.model.merchant.dto.MerchantDTOResponse;
import com.midorimart.managementsystem.model.productUnit.dto.ProductUnitDTOResponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDetailDTOResponse{
    private String slug;
    private String sku;
    private CategoryDTOResponse category;
    private String title;
    private double price;
    private int discount;
    private List<String> thumbnails;
    private String description;
    private String status;
    private Date created_at;
    private Date updated_at;
    private int deleted;
    private double amount;
    private MerchantDTOResponse merchant;
    private ProductUnitDTOResponse unit;
    private List<CommentDTOResponse> comments;
}
