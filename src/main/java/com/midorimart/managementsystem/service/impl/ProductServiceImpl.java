package com.midorimart.managementsystem.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.midorimart.managementsystem.entity.Product;
import com.midorimart.managementsystem.model.mapper.ProductMapper;
import com.midorimart.managementsystem.model.product.dto.ProductDTOCreate;
import com.midorimart.managementsystem.model.product.dto.ProductDTOResponse;
import com.midorimart.managementsystem.repository.ProductRepository;
import com.midorimart.managementsystem.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;

    @Override
    public void updateDeletedById(int id) {
        Optional<Product> productOptional = repository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setDeleted(1);
            product = repository.save(product);
        }
    }

    @Override
    public Map<String, List<ProductDTOResponse>> findAllProduct() {
        Map<String, List<ProductDTOResponse>> wrapper = new HashMap<>();
        List<Product> productList = repository.findAll();
        List<ProductDTOResponse> productDTOResponses = new ArrayList<>();
        ProductDTOResponse productDTOResponse;
        for (Product product : productList) {
            productDTOResponse = ProductMapper.toProductDTOResponse(product);
            productDTOResponses.add(productDTOResponse);
        }
        wrapper.put("product", productDTOResponses);
        return wrapper;
    }

    @Override
    public Map<String, ProductDTOResponse> addNewProduct(Map<String, ProductDTOCreate> productDTOMap) {
        ProductDTOCreate productDTOCreate = productDTOMap.get("product");
        Product product = ProductMapper.toProduct(productDTOCreate);

        product = repository.save(product);
        ProductDTOResponse productDTOResponse = ProductMapper.toProductDTOResponse(product);
        Map<String, ProductDTOResponse> wrapper = new HashMap<>();
        wrapper.put("product", productDTOResponse);
        return wrapper;
    }
}
