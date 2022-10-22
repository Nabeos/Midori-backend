package com.midorimart.managementsystem.model.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.midorimart.managementsystem.entity.Category;
import com.midorimart.managementsystem.entity.Country;
import com.midorimart.managementsystem.entity.Gallery;
import com.midorimart.managementsystem.entity.Merchant;
import com.midorimart.managementsystem.entity.Product;
import com.midorimart.managementsystem.model.category.dto.CategoryDTOCreate;
import com.midorimart.managementsystem.model.category.dto.CategoryDTOResponse;
import com.midorimart.managementsystem.model.country.dto.CountryDTOResponse;
import com.midorimart.managementsystem.model.merchant.dto.MerchantDTOResponse;
import com.midorimart.managementsystem.model.merchant.dto.MerchantUserDTOResponse;
import com.midorimart.managementsystem.model.product.dto.ProductDTOCreate;
import com.midorimart.managementsystem.model.product.dto.ProductDTOResponse;
import com.midorimart.managementsystem.model.product.dto.ProductDetailDTOResponse;
import com.midorimart.managementsystem.model.productUnit.dto.ProductUnitDTOResponse;

public class ProductMapper {
    public static ProductDTOResponse toProductDTOResponse(Product product) {
        return ProductDTOResponse.builder()
                .id(product.getId())
                .slug(product.getSlug())
                .sku(product.getSku())
                .title(product.getTitle())
                .thumbnails(toImageDTOResponse(product.getGalleries()))
                .status(product.getStatus())
                .description(product.getDescription())
                .deleted(product.getDeleted())
                .price(product.getPrice())
                .discount(product.getDiscount())
                .category(toCategoryDTOResponse(product.getCategory()))
                .created_at(product.getCreated_at())
                .updated_at(product.getUpdated_at())
                .merchant(toMerchantDtoResponse(product.getMerchant()))
                .amount(product.getAmount())
                .unit(ProductUnitDTOResponse.builder().id(product.getUnit().getId()).name(product.getUnit().getName()).build())
                .build();
    }

    public static Product toProduct(ProductDTOCreate productDTOCreate) {
        Date now = new Date();
        return Product.builder()
                .title(productDTOCreate.getTitle())
                .description(productDTOCreate.getDescription())
                .price(productDTOCreate.getPrice())
                .created_at(now)
                .updated_at(now)
                .discount(0)
                .deleted(0)
                .status("in_stock")
                .build();
    }

    public static CategoryDTOResponse toCategoryDTOResponse(Category cate) {
        return CategoryDTOResponse.builder()
                .id(cate.getId())
                .name(cate.getName())
                .build();
    }

    public static MerchantDTOResponse toMerchantDtoResponse(Merchant merchant) {
        return MerchantDTOResponse.builder().id(merchant.getId()).name(merchant.getMerchantName())
                .country(toCountryDTOResponse(merchant.getCountry()))
                .user(MerchantUserDTOResponse.builder().email(merchant.getUser().getEmail())
                        .fullname(merchant.getUser().getFullname()).build())
                .build();
    }

    public static CountryDTOResponse toCountryDTOResponse(Country country) {
        return CountryDTOResponse.builder().code(country.getCode()).name(country.getName()).build();
    }

    public static Category toCategory(CategoryDTOCreate categoryDTOCreate) {
        return Category.builder().name(categoryDTOCreate.getName()).build();
    }

    private static List<String> toImageDTOResponse(List<Gallery> galleries) {
        List<String> imageUrl = new ArrayList<>();

        for (Gallery gallery : galleries) {
            imageUrl.add(gallery.getThumbnail());
        }

        return imageUrl;
    }

    public static ProductDetailDTOResponse toProductDetail(Product product) {
        return ProductDetailDTOResponse.builder()
                .slug(product.getSlug())
                .sku(product.getSku())
                .title(product.getTitle())
                .thumbnails(toImageDTOResponse(product.getGalleries()))
                .status(product.getStatus())
                .description(product.getDescription())
                .deleted(product.getDeleted())
                .price(product.getPrice())
                .discount(product.getDiscount())
                .category(toCategoryDTOResponse(product.getCategory()))
                .created_at(product.getCreated_at())
                .updated_at(product.getUpdated_at())
                .merchant(toMerchantDtoResponse(product.getMerchant()))
                .amount(product.getAmount())
                .unit(ProductUnitDTOResponse.builder().id(product.getUnit().getId()).name(product.getUnit().getName()).build())
                .comments(CommentMapper.toListCommentDTOResponse(product.getComments()))
                .build();
    }
}
