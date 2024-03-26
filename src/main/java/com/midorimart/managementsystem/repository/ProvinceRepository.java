package com.midorimart.managementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.midorimart.managementsystem.entity.Province;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Integer>{

}
