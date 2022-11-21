package com.midorimart.managementsystem.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.midorimart.managementsystem.entity.Country;
import com.midorimart.managementsystem.model.country.dto.CountryDTOResponse;
import com.midorimart.managementsystem.model.mapper.ProductMapper;
import com.midorimart.managementsystem.repository.CountryRepository;
import com.midorimart.managementsystem.service.CountryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService{
    private final CountryRepository countryRepository;

    @Override
    public Map<String, CountryDTOResponse> getCountryByCode(String code) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, List<CountryDTOResponse>> getAllCountries() {
        List<Country> countries = countryRepository.findAll();
        return buildDTOResponse(countries);
    }

    private Map<String, List<CountryDTOResponse>> buildDTOResponse(List<Country> countries) {
        List<CountryDTOResponse> countryDTOResponses = countries.stream().map(ProductMapper::toCountryDTOResponse).collect(Collectors.toList());
        Map<String, List<CountryDTOResponse>> wrapper = new HashMap<>();
        wrapper.put("countries", countryDTOResponses);
        return wrapper;
    }
}
