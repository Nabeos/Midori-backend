package com.midorimart.managementsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.midorimart.managementsystem.entity.District;

@Repository
public interface DistrictRepository extends JpaRepository<District, Integer> {

    List<District> findByProvinceId(String id);

}
