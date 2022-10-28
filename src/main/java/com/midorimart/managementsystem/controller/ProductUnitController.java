package com.midorimart.managementsystem.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.midorimart.managementsystem.service.ProductUnitService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@Tag(name = "Product Unit API")
@CrossOrigin
@RequiredArgsConstructor
public class ProductUnitController {
    private final ProductUnitService productUnitService;
}
