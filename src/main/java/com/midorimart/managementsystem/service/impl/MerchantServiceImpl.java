package com.midorimart.managementsystem.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.midorimart.managementsystem.entity.Merchant;
import com.midorimart.managementsystem.entity.User;
import com.midorimart.managementsystem.model.mapper.MerchantMapper;
import com.midorimart.managementsystem.model.mapper.ProductMapper;
import com.midorimart.managementsystem.model.merchant.dto.MerchantDTOCreate;
import com.midorimart.managementsystem.model.merchant.dto.MerchantDTOResponse;
import com.midorimart.managementsystem.repository.MerchantRepository;
import com.midorimart.managementsystem.service.MerchantService;
import com.midorimart.managementsystem.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MerchantServiceImpl implements MerchantService {
    private final MerchantRepository merchantRepository;
    private final UserService userService;

    @Override
    public Map<String, MerchantDTOResponse> addNewMerchant(Map<String, MerchantDTOCreate> merchantDTOMap) {
        MerchantDTOCreate merchantDTOCreate = merchantDTOMap.get("merchant");
        Merchant merchant = MerchantMapper.toMerchant(merchantDTOCreate);
        User user = userService.getUserLogin();
        merchant.setUser(user);
        merchant = merchantRepository.save(merchant);
        MerchantDTOResponse merchantDTOResponse = MerchantMapper.toMerchantDTOResponse(merchant);
        Map<String, MerchantDTOResponse> wrapper = new HashMap<>();
        wrapper.put("merchant", merchantDTOResponse);
        return buildDTOResponse(merchant);
    }

    @Override
    public Map<String, List<MerchantDTOResponse>> getAllMerchant() {
        List<Merchant> merchants = merchantRepository.findAll();
        List<MerchantDTOResponse> merchantDTOResponses = merchants.stream().map(ProductMapper::toMerchantDtoResponse)
                .collect(Collectors.toList());
        return buildDTOResponse(merchantDTOResponses);
    }

    private Map<String, MerchantDTOResponse> buildDTOResponse(Merchant merchant) {
        MerchantDTOResponse merchantDTOResponse = MerchantMapper.toMerchantDTOResponse(merchant);
        Map<String, MerchantDTOResponse> wrapper = new HashMap<>();
        wrapper.put("merchant", merchantDTOResponse);
        return wrapper;
    }

    private Map<String, List<MerchantDTOResponse>> buildDTOResponse(List<MerchantDTOResponse> merchantDTOResponses) {
        Map<String, List<MerchantDTOResponse>> wrapper = new HashMap<>();
        wrapper.put("merchants", merchantDTOResponses);
        return wrapper;
    }
}
