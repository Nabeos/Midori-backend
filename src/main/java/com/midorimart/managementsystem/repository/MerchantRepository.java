package com.midorimart.managementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.midorimart.managementsystem.entity.Merchant;

public interface MerchantRepository extends JpaRepository<Merchant, Integer> {

}
