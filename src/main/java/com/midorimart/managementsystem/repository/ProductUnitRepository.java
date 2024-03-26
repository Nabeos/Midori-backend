package com.midorimart.managementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.midorimart.managementsystem.entity.ProductUnit;
@Repository
public interface ProductUnitRepository extends JpaRepository<ProductUnit, Integer>{

}
