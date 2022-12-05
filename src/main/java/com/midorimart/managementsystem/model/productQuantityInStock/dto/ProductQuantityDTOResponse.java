package com.midorimart.managementsystem.model.productQuantityInStock.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductQuantityDTOResponse {
    private String expiryDate;
}
