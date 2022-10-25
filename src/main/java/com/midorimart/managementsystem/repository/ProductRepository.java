package com.midorimart.managementsystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.midorimart.managementsystem.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findBySlug(String slug);

    @Query(value = "select * from Product where title Like ?1 or slug LIKE ?1", nativeQuery = true)
    List<Product> findByTitleOrSlug(String query);

    Optional<Product> findByTitle(String title);

}
