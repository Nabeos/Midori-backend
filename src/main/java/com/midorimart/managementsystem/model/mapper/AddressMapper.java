package com.midorimart.managementsystem.model.mapper;

import com.midorimart.managementsystem.entity.District;
import com.midorimart.managementsystem.entity.Province;
import com.midorimart.managementsystem.entity.Ward;
import com.midorimart.managementsystem.model.address.dto.DistrictDTOResponse;
import com.midorimart.managementsystem.model.address.dto.ProvinceDTOResponse;
import com.midorimart.managementsystem.model.address.dto.WardDTOResponse;

public class AddressMapper {
    public static ProvinceDTOResponse toProvinceDTOResponse(Province province) {
        return ProvinceDTOResponse.builder().id(province.getProvinceId()).name(province.getName()).build();
    }

    public static DistrictDTOResponse toDistrictDTOResponse(District district) {
        return DistrictDTOResponse.builder().id(district.getDistrictId()).name(district.getName()).build();
    }

    public static WardDTOResponse toWardDTOResponse(Ward ward) {
        return WardDTOResponse.builder().id(ward.getWardId()).name(ward.getName()).build();
    }

}
