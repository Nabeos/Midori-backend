package com.midorimart.managementsystem.model.product.dto;

import java.util.List;

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
    private int merchantId;
    private List<String> origin;
    private List<Double> star;
}
