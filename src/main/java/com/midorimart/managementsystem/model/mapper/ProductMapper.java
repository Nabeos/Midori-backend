package com.midorimart.managementsystem.model.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.midorimart.managementsystem.entity.Category;
import com.midorimart.managementsystem.entity.Country;
import com.midorimart.managementsystem.entity.Gallery;
import com.midorimart.managementsystem.entity.Merchant;
import com.midorimart.managementsystem.entity.Product;
import com.midorimart.managementsystem.entity.ProductQuantity;
import com.midorimart.managementsystem.model.category.dto.CategoryDTOCreate;
import com.midorimart.managementsystem.model.category.dto.CategoryDTOResponse;
import com.midorimart.managementsystem.model.country.dto.CountryDTOResponse;
import com.midorimart.managementsystem.model.merchant.dto.MerchantDTOResponse;
import com.midorimart.managementsystem.model.product.dto.ProductDTOCreate;
import com.midorimart.managementsystem.model.product.dto.ProductDTOResponse;
import com.midorimart.managementsystem.model.product.dto.ProductDetailDTOResponse;
import com.midorimart.managementsystem.model.productQuantityInStock.dto.ProductQuantityDTOResponse;
import com.midorimart.managementsystem.model.productUnit.dto.ProductUnitDTOResponse;
import com.midorimart.managementsystem.utils.DateHelper;

public class ProductMapper {
    public static ProductDTOResponse toProductDTOResponse(Product product) {
        return ProductDTOResponse.builder()
                .id(product.getId())
                .slug(product.getSlug())
                .sku(product.getSku())
                .title(product.getTitle())
                .thumbnails(toImageDTOResponse(product.getGalleries()))
                .status(getStatus(product.getStatus()))
                .deleted(product.getDeleted())
                .price(product.getPrice())
                .discount(product.getDiscount())
                .origin(product.getCountry()!=null?product.getCountry().getCode():"VNI")
                .category(toCategoryDTOResponse(product.getCategory()))
                .created_at(DateHelper.convertDate(product.getCreated_at()))
                .updated_at(DateHelper.convertDate(product.getUpdated_at()))
                .merchant(toMerchantDtoResponse(product.getMerchant()))
                .amount(product.getAmount())
                .quantity(product.getQuantity())
                .unit(ProductUnitDTOResponse.builder().id(product.getUnit().getId()).name(product.getUnit().getName())
                        .build())
                // .productQuantityInStock(toProductQuantityDTOResponseList(product.getProductQuantities()))
                .build();
    }

    private static String getStatus(String status) {
        switch(status){
            case "0":
                return "Hết Hàng";
            case "1":
                return "Sắp Hết Hàng";
            case "2":
                return "Còn Hàng";
            default:
                return "Lỗi";
        }
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
                .status("0")
                .country(Country.builder().code(productDTOCreate.getOrigin()).build())
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
                .build();
    }

    public static CountryDTOResponse toCountryDTOResponse(Country country) {
        return CountryDTOResponse.builder().code(country.getCode()).name(country.getName()).thumbnail(country.getImage()).build();
    }

    public static Category toCategory(CategoryDTOCreate categoryDTOCreate) {
        return Category.builder().name(categoryDTOCreate.getName()).build();
    }

    public static List<String> toImageDTOResponse(List<Gallery> galleries) {
        List<String> imageUrl = new ArrayList<>();

        for (Gallery gallery : galleries) {
            imageUrl.add(gallery.getThumbnail().replace("\\", "/"));
        }
        Collections.reverse(imageUrl);
        return imageUrl;
    }

    public static ProductDetailDTOResponse toProductDetail(Product product) {
        return ProductDetailDTOResponse.builder()
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
                .origin(product.getCountry().getCode())
                .category(toCategoryDTOResponse(product.getCategory()))
                .created_at(DateHelper.convertDate(product.getCreated_at()))
                .updated_at(DateHelper.convertDate(product.getUpdated_at()))
                .merchant(toMerchantDtoResponse(product.getMerchant()))
                .amount(product.getAmount())
                .quantity(product.getQuantity())
                .unit(ProductUnitDTOResponse.builder().id(product.getUnit().getId()).name(product.getUnit().getName())
                        .build())
                .comments(CommentMapper.toListCommentDTOResponse(product.getComments()))
                // .productQuantityInStock(toProductQuantityDTOResponseList(product.getProductQuantities()))
                .build();
    }

    public static List<ProductQuantityDTOResponse> toProductQuantityDTOResponseList(List<ProductQuantity> product) {
        return product.stream().map(ProductMapper::toProductQuantityDTOResponse).collect(Collectors.toList());
    }

    public static ProductQuantityDTOResponse toProductQuantityDTOResponse(ProductQuantity product) {
        return ProductQuantityDTOResponse.builder().quantity(product.getQuantity())
                .manufacturingDate(product.getManufacturingDate()).expiryDate(DateHelper.convertDate(product.getExpiryDate()))
                .createdDate(product.getCreatedDate()).updatedDate(product.getUpdatedDate()).build();
    }
}
