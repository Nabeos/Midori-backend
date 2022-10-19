package com.midorimart.managementsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.midorimart.managementsystem.entity.Gallery;

public interface GalleryRepository extends JpaRepository<Gallery, Integer>{

    List<Gallery> findByProductId(int id);

}
