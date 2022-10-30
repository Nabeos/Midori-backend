package com.midorimart.managementsystem.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.midorimart.managementsystem.exception.custom.CustomBadRequestException;
import com.midorimart.managementsystem.exception.custom.CustomNotFoundException;
import com.midorimart.managementsystem.model.category.dto.CategoryDTOCreate;
import com.midorimart.managementsystem.model.category.dto.CategoryDTOResponse;
import com.midorimart.managementsystem.model.product.dto.ImageDTOResponse;
import com.midorimart.managementsystem.model.product.dto.ProductDTOCreate;
import com.midorimart.managementsystem.model.product.dto.ProductDTOFilter;
import com.midorimart.managementsystem.model.product.dto.ProductDTOResponse;
import com.midorimart.managementsystem.model.product.dto.ProductDetailDTOResponse;

public interface ProductService {

    public void updateDeletedById(int id);

    public Map<String, List<ProductDTOResponse>> findAllProduct();

    public Map<String, String> addNewProduct(Map<String, ProductDTOCreate> productDTOMap) throws CustomBadRequestException, CustomNotFoundException;

    public Map<String, CategoryDTOResponse> addNewCategory(Map<String, CategoryDTOCreate> categoryMap) throws CustomBadRequestException;

    public Map<String, Object> getProductByCategoryId(ProductDTOFilter filter);

    public Map<String, List<CategoryDTOResponse>> getAllCategories();

    public Map<String, ImageDTOResponse> uploadImage(MultipartFile[] multipartFiles) throws IllegalStateException, IOException;

    public Map<String, List<ProductDTOResponse>> searchProduct(String productName);

    public Map<String, ProductDetailDTOResponse> getProductBySlug(String slug) throws CustomNotFoundException;

    public Map<String, List<ProductDTOResponse>> getBestSellerInEachCategory(int categoryId);

}
