package com.midorimart.managementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.midorimart.managementsystem.entity.DeliveryNoteDetail;

@Repository
public interface DeliveryNoteDetailRepository extends JpaRepository<DeliveryNoteDetail, Integer>{

}
