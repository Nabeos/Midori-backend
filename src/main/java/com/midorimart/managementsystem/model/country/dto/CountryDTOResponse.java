package com.midorimart.managementsystem.model.country.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryDTOResponse {
    private String code;
    private String name;
    private String thumbnail;
}
