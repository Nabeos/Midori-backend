package com.midorimart.managementsystem.model.mapper;

import java.util.Date;

import com.midorimart.managementsystem.entity.Merchant;
import com.midorimart.managementsystem.model.country.dto.CountryDTOResponse;
import com.midorimart.managementsystem.model.merchant.dto.MerchantDTOCreate;
import com.midorimart.managementsystem.model.merchant.dto.MerchantDTOResponse;

public class MerchantMapper {

    public static Merchant toMerchant(MerchantDTOCreate merchantDTOCreate) {
        Date now = new Date();
        return Merchant.builder().merchantName(merchantDTOCreate.getName()).createdAt(now)
                .country(null)
                .build();
    }

    public static MerchantDTOResponse toMerchantDTOResponse(Merchant merchant) {
        return MerchantDTOResponse.builder()
                .id(merchant.getId())
                .name(merchant.getMerchantName())
                .country(CountryDTOResponse.builder().code(merchant.getCountry().getCode())
                        .name(merchant.getCountry().getName()).build())
                .build();
    }

}
