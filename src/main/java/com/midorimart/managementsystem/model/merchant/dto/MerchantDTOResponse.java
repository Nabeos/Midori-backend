package com.midorimart.managementsystem.model.merchant.dto;

import com.midorimart.managementsystem.model.country.dto.CountryDTOResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MerchantDTOResponse {
    private int id;
    private CountryDTOResponse country;
    private String name;
}
