package com.midorimart.managementsystem.model.mapper;

import java.util.Date;

import com.midorimart.managementsystem.entity.Category;
import com.midorimart.managementsystem.entity.Product;
import com.midorimart.managementsystem.model.category.dto.CategoryDTOResponse;
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
                .category(toCategoryDTOResponse(product.getCategory()))
                .created_at(product.getCreated_at())
                .updated_at(product.getUpdated_at())
                .build();
    }

    public static Product toProduct(ProductDTOCreate productDTOCreate) {
        Date now = new Date();
        return Product.builder()
                .title(productDTOCreate.getTitle())
                .thumbnails(productDTOCreate.getThumbnails())
                .description(productDTOCreate.getDescription())
                .price(productDTOCreate.getPrice())
                .created_at(now)
                .updated_at(now)
                .discount(0)
                .deleted(0)
                .status("in_stock")
                .build();
    }

    private static CategoryDTOResponse toCategoryDTOResponse(Category cate) {
        return CategoryDTOResponse.builder()
                .id(cate.getId())
                .name(cate.getName())
                .build();
    }
}
