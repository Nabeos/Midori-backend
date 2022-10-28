package com.midorimart.managementsystem.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
import com.midorimart.managementsystem.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@Tag(name = "Product API")
public class ProductController {
    private final ProductService service;

    @Operation(summary = "Search all products")
    @GetMapping("/product-management/search-product")
    public Map<String, List<ProductDTOResponse>> searchProduct(
            @RequestParam(name = "title", required = false) String productName) {
        return service.searchProduct(productName);
    }

    @Operation(summary = "Get Product Detail")
    @GetMapping("/product-management/products/{slug}")
    public Map<String, ProductDetailDTOResponse> getProductBySlug(@PathVariable String slug)
            throws CustomNotFoundException {
        return service.getProductBySlug(slug);
    }

    @Operation(summary = "Get Products by category, discount, price, merchant, star rate")
    @GetMapping("/product-management/products")
    public Map<String, Object> getProductByCategoryId(
            @RequestParam(name = "category", defaultValue = "0", required = false) Integer categoryId,
            @RequestParam(name = "limit", defaultValue = "20") Integer limit,
            @RequestParam(name = "offset", defaultValue = "0") Integer offset) {
        ProductDTOFilter filter = ProductDTOFilter.builder().categoryId(categoryId).offset(offset).limit(limit)
                .build();
        return service.getProductByCategoryId(filter);
    }

    @Operation(summary = "Get All Categories")
    @GetMapping("/category-management/categories")
    public Map<String, List<CategoryDTOResponse>> getAllCategories() {
        return service.getAllCategories();
    }

    @Operation(summary = "Add new Product")
    @PostMapping("/api/v1/product-management/products")
    public Map<String, String> addNewProduct(@RequestBody Map<String, ProductDTOCreate> productDTOMap)
            throws CustomBadRequestException, CustomNotFoundException {
        return service.addNewProduct(productDTOMap);
    }

    @Operation(summary = "Upload image after add new Product")
    @PostMapping("/api/v1/product-management/products/{slug}/images")
    public Map<String, ImageDTOResponse> uploadImage(@RequestParam("files") MultipartFile[] files)
            throws IllegalStateException, IOException {
        return service.uploadImage(files);
    }

    @Operation(summary = "Add new Category")
    @PostMapping("/api/v1/category-management/categories")
    public Map<String, CategoryDTOResponse> addNewCategory(@RequestBody Map<String, CategoryDTOCreate> categoryMap)
            throws CustomBadRequestException {
        return service.addNewCategory(categoryMap);
    }

    @Operation(summary = "Delete product by update deleted id")
    @PutMapping("/api/v1/product-management/products/{id}")
    public void deleteUser(@PathVariable("id") int id) {
        service.updateDeletedById(id);
    }

}
