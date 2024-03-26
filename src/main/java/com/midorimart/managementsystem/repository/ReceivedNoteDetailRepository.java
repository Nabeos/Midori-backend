package com.midorimart.managementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.midorimart.managementsystem.entity.ReceivedNoteDetail;

@Repository
public interface ReceivedNoteDetailRepository extends JpaRepository<ReceivedNoteDetail, Integer>{

}
