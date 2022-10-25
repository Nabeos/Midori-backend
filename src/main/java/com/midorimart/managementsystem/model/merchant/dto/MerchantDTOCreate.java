package com.midorimart.managementsystem.model.merchant.dto;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MerchantDTOCreate {
    private String name;
    private String countryCode;
    private int userId;
    private Date createdAt;
}
