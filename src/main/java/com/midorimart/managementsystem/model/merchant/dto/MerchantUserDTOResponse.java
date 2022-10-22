package com.midorimart.managementsystem.model.merchant.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MerchantUserDTOResponse {
    private String email;
    private String fullname;
}
