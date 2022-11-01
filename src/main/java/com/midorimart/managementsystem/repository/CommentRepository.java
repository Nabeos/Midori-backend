package com.midorimart.managementsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.midorimart.managementsystem.entity.Comment;
@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer>{

    @Query(value = "select AVG(star_rate) as star_rate from Comment where product_id = ?1", nativeQuery = true)
    Optional<Double> findAvgStarByProduct(int id);

}
