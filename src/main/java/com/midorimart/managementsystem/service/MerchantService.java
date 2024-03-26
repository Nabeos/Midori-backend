package com.midorimart.managementsystem.service;

import java.util.List;
import java.util.Map;

import com.midorimart.managementsystem.model.merchant.dto.MerchantDTOCreate;
import com.midorimart.managementsystem.model.merchant.dto.MerchantDTOResponse;

public interface MerchantService {

    Map<String, MerchantDTOResponse> addNewMerchant(Map<String, MerchantDTOCreate> merchantDTOMap);

    Map<String, List<MerchantDTOResponse>> getAllMerchant();

}
