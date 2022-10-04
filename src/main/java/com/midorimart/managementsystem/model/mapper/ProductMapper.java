package com.midorimart.managementsystem.model.mapper;

import com.midorimart.managementsystem.entity.Category;
import com.midorimart.managementsystem.entity.Product;
import com.midorimart.managementsystem.model.product.dto.ProductDTOCreate;
import com.midorimart.managementsystem.model.product.dto.ProductDTOResponse;

public class ProductMapper {
    public static ProductDTOResponse toProductDTOResponse(Product product) {
        return ProductDTOResponse.builder()
                .id(product.getId())
                .title(product.getTitle())
                .thumbnails(product.getThumbnails())
                .status(product.getStatus())
                .description(product.getDescription())
                .deleted(product.getDeleted())
                .price(product.getPrice())
                .discount(product.getDiscount())
                .category(getCategoryDetail(product.getCategory()))
                .created_at(product.getCreated_at())
                .updated_at(product.getUpdated_at())
                .build();
    }

    private static Category getCategoryDetail(Category cate) {
        return Category.builder()
                .id(cate.getId())
                .name(cate.getName())
                .build();
    }

    public static Product toProduct(ProductDTOCreate productDTOCreate) {
        return Product.builder()
                .category(getCategoryDetail(productDTOCreate.getCategory()))
                .title(productDTOCreate.getTitle())
                .thumbnails(productDTOCreate.getThumbnails())
                .description(productDTOCreate.getDescription())
                .price(productDTOCreate.getPrice())
                .build();
    }
}
