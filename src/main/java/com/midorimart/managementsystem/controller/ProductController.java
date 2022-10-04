package com.midorimart.managementsystem.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.midorimart.managementsystem.entity.Product;
import com.midorimart.managementsystem.model.product.dto.ProductDTOCreate;
import com.midorimart.managementsystem.model.product.dto.ProductDTOResponse;
import com.midorimart.managementsystem.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;

    @GetMapping("/products")
    public Map<String, List<ProductDTOResponse>> getAllProduct() {
        return service.findAllProduct();
    }

    @PostMapping("/product")
    public Map<String, ProductDTOResponse> addNewProduct(@RequestBody Map<String, ProductDTOCreate> productDTOMap) {
        return service.addNewProduct(productDTOMap);
    }

    @DeleteMapping("/product/{id}")
    public void deleteUser(@PathVariable("id") int id) {
        service.updateDeletedById(id);
    }
}
