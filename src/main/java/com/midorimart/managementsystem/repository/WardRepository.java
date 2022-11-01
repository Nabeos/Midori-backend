package com.midorimart.managementsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.midorimart.managementsystem.entity.Ward;

@Repository
public interface WardRepository extends JpaRepository<Ward, Integer> {

    List<Ward> findByDistrictId(String id);

}
