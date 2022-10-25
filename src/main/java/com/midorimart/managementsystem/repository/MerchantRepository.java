package com.midorimart.managementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.midorimart.managementsystem.entity.Merchant;
@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Integer> {

}
