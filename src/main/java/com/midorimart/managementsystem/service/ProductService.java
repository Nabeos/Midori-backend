package com.midorimart.managementsystem.service;

import java.util.List;
import java.util.Map;

import com.midorimart.managementsystem.model.category.dto.CategoryDTOCreate;
import com.midorimart.managementsystem.model.category.dto.CategoryDTOResponse;
import com.midorimart.managementsystem.model.product.dto.ProductDTOCreate;
import com.midorimart.managementsystem.model.product.dto.ProductDTOFilter;
import com.midorimart.managementsystem.model.product.dto.ProductDTOResponse;

public interface ProductService {

    public void updateDeletedById(int id);

    public Map<String, List<ProductDTOResponse>> findAllProduct();

    public Map<String, ProductDTOResponse> addNewProduct(Map<String, ProductDTOCreate> productDTOMap);

    public Map<String, CategoryDTOResponse> addNewCategory(Map<String, CategoryDTOCreate> categoryMap);

    public Map<String, Object> getProductByCategoryId(ProductDTOFilter filter);

    public Map<String, List<CategoryDTOResponse>> getAllCategories();

}
