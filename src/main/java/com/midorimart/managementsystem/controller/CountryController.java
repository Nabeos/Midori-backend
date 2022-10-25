package com.midorimart.managementsystem.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.midorimart.managementsystem.model.country.dto.CountryDTOResponse;
import com.midorimart.managementsystem.service.CountryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@Tag(name = "API for Country")
@RequiredArgsConstructor
@CrossOrigin
public class CountryController {
    private final CountryService countryService;
    @GetMapping("/country/{code}")
    @Operation(summary = "Get Country By Code")
    public Map<String, CountryDTOResponse> getCountryByCode(@PathVariable String code){
return countryService.getCountryByCode(code);
    }
}
