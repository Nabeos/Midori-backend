package com.midorimart.managementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.midorimart.managementsystem.entity.Comment;
@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer>{

}
