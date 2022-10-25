package com.midorimart.managementsystem.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.midorimart.managementsystem.model.country.dto.CountryDTOResponse;
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
}
