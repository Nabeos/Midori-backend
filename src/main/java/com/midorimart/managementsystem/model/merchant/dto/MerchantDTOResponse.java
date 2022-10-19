package com.midorimart.managementsystem.model.merchant.dto;

import java.util.Date;

import com.midorimart.managementsystem.model.country.dto.CountryDTOResponse;
import com.midorimart.managementsystem.model.users.UserDTOResponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MerchantDTOResponse {
    private int id;
    private CountryDTOResponse country;
    private String name;
    private Date createdAt;
    private UserDTOResponse user;
}
