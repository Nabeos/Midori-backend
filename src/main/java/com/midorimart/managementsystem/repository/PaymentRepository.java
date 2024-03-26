package com.midorimart.managementsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.midorimart.managementsystem.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer>{
  @Query(value = "select * from Payment where order_id = ?1", nativeQuery = true)
  Optional<Payment> findByOrderId(int orderId);

  // Optional<Payment> findByVnpTxnRef();
}
