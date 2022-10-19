package com.midorimart.managementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.midorimart.managementsystem.entity.Merchant;
import com.midorimart.managementsystem.model.merchant.dto.MerchantDTOResponse;

public interface MerchantRepository extends JpaRepository<Merchant, Integer> {

}
