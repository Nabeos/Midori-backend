package com.midorimart.managementsystem.model.category.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDTOResponse {
    private int id;
    private String name;
}
