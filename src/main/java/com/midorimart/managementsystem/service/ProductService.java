package com.midorimart.managementsystem.service;

import java.util.List;
import java.util.Map;

import com.midorimart.managementsystem.model.product.dto.ProductDTOCreate;
import com.midorimart.managementsystem.model.product.dto.ProductDTOResponse;

public interface ProductService {

    void updateDeletedById(int id);

    Map<String, List<ProductDTOResponse>> findAllProduct();

    Map<String, ProductDTOResponse> addNewProduct(Map<String, ProductDTOCreate> productDTOMap);

}
