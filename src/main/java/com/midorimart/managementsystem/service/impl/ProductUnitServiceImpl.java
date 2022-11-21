package com.midorimart.managementsystem.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.midorimart.managementsystem.entity.ProductUnit;
import com.midorimart.managementsystem.model.productUnit.dto.ProductUnitDTOResponse;
import com.midorimart.managementsystem.repository.ProductUnitRepository;
import com.midorimart.managementsystem.service.ProductUnitService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductUnitServiceImpl implements ProductUnitService {
    private final ProductUnitRepository productUnitRepository;

    @Override
    public Map<String, List<ProductUnitDTOResponse>> getAllUnit() {
        List<ProductUnit> productUnits = productUnitRepository.findAll();
        List<ProductUnitDTOResponse> productUnitDTOResponses = productUnits.stream()
        .map((e)->(ProductUnitDTOResponse.builder().id(e.getId()).name(e.getName()).build())).collect(Collectors.toList());
        return buildDTOResponse(productUnitDTOResponses);
    }

    private Map<String, List<ProductUnitDTOResponse>> buildDTOResponse(List<ProductUnitDTOResponse> productUnitDTOResponses) {
        Map<String, List<ProductUnitDTOResponse>> wrapper = new HashMap<>();
        wrapper.put("units", productUnitDTOResponses);
        return wrapper;
    }

}
