package com.midorimart.managementsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.midorimart.managementsystem.entity.Gallery;

@Repository
public interface GalleryRepository extends JpaRepository<Gallery, Integer> {

    List<Gallery> findByProductId(int id);

}
