package com.midorimart.managementsystem.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.midorimart.managementsystem.model.product.dto.ProductDTOCreate;
import com.midorimart.managementsystem.model.product.dto.ProductDTOResponse;
import com.midorimart.managementsystem.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/productManagement")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;

    @GetMapping("/getAllProducts")
    public Map<String, List<ProductDTOResponse>> getAllProduct() {
        return service.findAllProduct();
    }

    @PostMapping("/addNewProduct")
    public Map<String, ProductDTOResponse> addNewProduct(@RequestBody Map<String, ProductDTOCreate> productDTOMap) {
        return service.addNewProduct(productDTOMap);
    }

    @PutMapping("/deleteProduct/{id}")
    public void deleteUser(@PathVariable("id") int id) {
        service.updateDeletedById(id);
    }
}
