package com.midorimart.managementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.midorimart.managementsystem.entity.Country;
@Repository
public interface CountryRepository extends JpaRepository<Country, String>{

    Country findByCode(String origin);

}
