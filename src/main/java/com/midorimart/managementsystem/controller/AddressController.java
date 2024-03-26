package com.midorimart.managementsystem.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.midorimart.managementsystem.model.address.dto.DistrictDTOResponse;
import com.midorimart.managementsystem.model.address.dto.ProvinceDTOResponse;
import com.midorimart.managementsystem.model.address.dto.WardDTOResponse;
import com.midorimart.managementsystem.service.AddressService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Address API")

public class AddressController {
    private final AddressService addressService;

    @Operation(summary = "Get All Provinces")
    @GetMapping("/provinces")
    public Map<String, List<ProvinceDTOResponse>> getProvinces(){
        return addressService.getProvinces();
    }

    @Operation(summary = "Get All Districts by Province Id")
    @GetMapping("/provinces/{province-id}/districts")
    public Map<String, List<DistrictDTOResponse>> getDistricts(@PathVariable(name = "province-id") String id){
        return addressService.getDistricts(id);
    }

    @Operation(summary = "Get All Wards by District Id")
    @GetMapping("/districts/{district-id}/wards")
    public Map<String, List<WardDTOResponse>> getWards(@PathVariable(name = "district-id") String id){
        return addressService.getWards(id);
    }


}
