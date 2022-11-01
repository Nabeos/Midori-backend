package com.midorimart.managementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.midorimart.managementsystem.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    
}
