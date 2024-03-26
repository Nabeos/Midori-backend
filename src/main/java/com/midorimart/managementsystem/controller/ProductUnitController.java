package com.midorimart.managementsystem.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.midorimart.managementsystem.model.productUnit.dto.ProductUnitDTOResponse;
import com.midorimart.managementsystem.service.ProductUnitService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@Tag(name = "Product Unit API")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class ProductUnitController {
    private final ProductUnitService productUnitService;
    @Operation(summary = "Get All Unit")
    @GetMapping("/units")
    public Map<String, List<ProductUnitDTOResponse>> getAllUnit(){
        return productUnitService.getAllUnit();
    }
}
