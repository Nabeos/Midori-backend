package com.midorimart.managementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.midorimart.managementsystem.entity.Country;

public interface CountryRepository extends JpaRepository<Country, String>{

}
