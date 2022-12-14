package com.midorimart.managementsystem.service;

import java.util.List;
import java.util.Map;

import com.midorimart.managementsystem.model.country.dto.CountryDTOResponse;

public interface CountryService {

    Map<String, CountryDTOResponse> getCountryByCode(String code);

    Map<String, List<CountryDTOResponse>> getAllCountries();

}
