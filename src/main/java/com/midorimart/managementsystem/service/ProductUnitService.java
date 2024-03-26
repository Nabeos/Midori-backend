package com.midorimart.managementsystem.service;

import java.util.List;
import java.util.Map;

import com.midorimart.managementsystem.model.productUnit.dto.ProductUnitDTOResponse;

public interface ProductUnitService {

    Map<String, List<ProductUnitDTOResponse>> getAllUnit();

}
