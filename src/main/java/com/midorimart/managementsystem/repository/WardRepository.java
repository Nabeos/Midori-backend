package com.midorimart.managementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.midorimart.managementsystem.entity.Ward;

@Repository
public interface WardRepository extends JpaRepository<Ward, Integer> {

}
