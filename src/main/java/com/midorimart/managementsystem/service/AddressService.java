package com.midorimart.managementsystem.service;

import java.util.List;
import java.util.Map;

import com.midorimart.managementsystem.model.address.dto.DistrictDTOResponse;
import com.midorimart.managementsystem.model.address.dto.ProvinceDTOResponse;
import com.midorimart.managementsystem.model.address.dto.WardDTOResponse;

public interface AddressService {

    Map<String, List<ProvinceDTOResponse>> getProvinces();

    Map<String, List<DistrictDTOResponse>> getDistricts(String id);

    Map<String, List<WardDTOResponse>> getWards(String id);

}
