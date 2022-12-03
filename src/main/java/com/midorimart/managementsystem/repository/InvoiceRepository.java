package com.midorimart.managementsystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.midorimart.managementsystem.entity.Invoice;
@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer>{

    List<Invoice> findByUserId(int userId);

    Optional<Invoice> findByUserIdAndOrderId(int id, int id2);

    Optional<Invoice> findByUserAndOrder(int id, int id2);

    Invoice findByOrderId(int i);

}
