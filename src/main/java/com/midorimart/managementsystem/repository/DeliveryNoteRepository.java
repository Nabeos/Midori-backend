package com.midorimart.managementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.midorimart.managementsystem.entity.DeliveryNote;

@Repository
public interface DeliveryNoteRepository extends JpaRepository<DeliveryNote, Integer>{

    DeliveryNote findByOrderId(int id);

    DeliveryNote findByOrder(int id);

}
