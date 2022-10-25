package com.midorimart.managementsystem.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.midorimart.managementsystem.model.merchant.dto.MerchantDTOCreate;
import com.midorimart.managementsystem.model.merchant.dto.MerchantDTOResponse;
import com.midorimart.managementsystem.service.MerchantService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequestMapping("/api")
@RequiredArgsConstructor
public class MerchantController {
    private final MerchantService merchantService;

    @Operation(summary = "Add Merchant")
    @PostMapping("/merchants")
    public Map<String, MerchantDTOResponse> addNewMerchant(@RequestBody Map<String, MerchantDTOCreate> merchantDTOMap){
        return merchantService.addNewMerchant(merchantDTOMap);
    }
}
