package com.midorimart.managementsystem.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.midorimart.managementsystem.entity.District;
import com.midorimart.managementsystem.entity.Province;
import com.midorimart.managementsystem.entity.Ward;
import com.midorimart.managementsystem.model.address.dto.DistrictDTOResponse;
import com.midorimart.managementsystem.model.address.dto.ProvinceDTOResponse;
import com.midorimart.managementsystem.model.address.dto.WardDTOResponse;
import com.midorimart.managementsystem.model.mapper.AddressMapper;
import com.midorimart.managementsystem.repository.DistrictRepository;
import com.midorimart.managementsystem.repository.ProvinceRepository;
import com.midorimart.managementsystem.repository.WardRepository;
import com.midorimart.managementsystem.service.AddressService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final ProvinceRepository provinceRepository;
    private final DistrictRepository districtRepository;
    private final WardRepository wardRepository;

    @Override
    public Map<String, List<ProvinceDTOResponse>> getProvinces() {
        List<Province> provinces = provinceRepository.findAll();
        List<ProvinceDTOResponse> provinceDTOResponses = provinces.stream().map(AddressMapper::toProvinceDTOResponse)
                .collect(Collectors.toList());
        Map<String, List<ProvinceDTOResponse>> wrapper = new HashMap<>();
        wrapper.put("provinces", provinceDTOResponses);
        return wrapper;
    }

    @Override
    public Map<String, List<DistrictDTOResponse>> getDistricts(String id) {
        List<District> districts = districtRepository.findByProvinceId(id);
        List<DistrictDTOResponse> districtDTOResponses = districts.stream().map(AddressMapper::toDistrictDTOResponse)
                .collect(Collectors.toList());
        Map<String, List<DistrictDTOResponse>> wrapper = new HashMap<>();
        wrapper.put("districts", districtDTOResponses);
        return wrapper;
    }

    @Override
    public Map<String, List<WardDTOResponse>> getWards(String id) {
        List<Ward> wards = wardRepository.findByDistrictId(id);
        List<WardDTOResponse> wardDTOResponses = wards.stream().map(AddressMapper::toWardDTOResponse)
                .collect(Collectors.toList());
        Map<String, List<WardDTOResponse>> wrapper = new HashMap<>();
        wrapper.put("wards", wardDTOResponses);
        return wrapper;
    }

}
