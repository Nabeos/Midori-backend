package com.midorimart.managementsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.midorimart.managementsystem.entity.ReceivedNote;

@Repository
public interface ReceivedNoteRepository extends JpaRepository<ReceivedNote, Integer>{

    @Query(value = "select * from Received_note where created_by = ?1 order by created_at desc OFFSET ?2 ROWS FETCH NEXT ?3 ROWS ONLY", nativeQuery = true)
    List<ReceivedNote> findByUserId(int id, int offset, int limit);

    @Query(value = "select * from Received_note where created_at between ?1 and ?2 order by created_at desc OFFSET ?3 ROWS FETCH NEXT ?4 ROWS ONLY", nativeQuery = true)
    List<ReceivedNote> findByDateRange(String firstDate, String secondDate, int offset, int limit);

    @Query(value = "select * from Received_note where merchant_id = ?1 order by created_at desc OFFSET ?2 ROWS FETCH NEXT ?3 ROWS ONLY", nativeQuery = true)
    List<ReceivedNote> findByMerchantId(int id, int offset, int limit);

}
