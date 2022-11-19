package com.midorimart.managementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.midorimart.managementsystem.entity.ReceivedNote;

@Repository
public interface ReceivedNoteRepository extends JpaRepository<Integer, ReceivedNote>{

}
