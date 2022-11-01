package com.midorimart.managementsystem.model.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDTOFilter {
    private int categoryId;
    private String priceAsc;
    private String priceDesc;
    private int limit;
    private int offset;
}
