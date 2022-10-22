package com.midorimart.managementsystem.model.merchant.dto;

import com.midorimart.managementsystem.model.country.dto.CountryDTOResponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MerchantDTOResponse {
    private int id;
    private CountryDTOResponse country;
    private String name;
    private MerchantUserDTOResponse user;
}
