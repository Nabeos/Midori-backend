package com.midorimart.managementsystem.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.midorimart.managementsystem.entity.Product;
import com.midorimart.managementsystem.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductController {
    private final ProductRepository repository;
    @GetMapping("/product")
    public List<Product> getAllProduct(){
        return repository.findAll();
    }
    @DeleteMapping("/product/{id}")
    public void deleteUser(@PathVariable("id") int id) {
        repository.deleteById(id);
    }
}
