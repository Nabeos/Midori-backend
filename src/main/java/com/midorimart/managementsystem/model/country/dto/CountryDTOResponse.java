package com.midorimart.managementsystem.model.country.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CountryDTOResponse {
    private String code;
    private String name;
}
