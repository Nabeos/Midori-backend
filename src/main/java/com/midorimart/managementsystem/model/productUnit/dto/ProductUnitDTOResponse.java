package com.midorimart.managementsystem.model.productUnit.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductUnitDTOResponse {
    private int id;
    private String name;
}
