package com.midorimart.managementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.midorimart.managementsystem.entity.ProductQuantity;

@Repository
public interface ProductQuantityRepository extends JpaRepository<ProductQuantity, Integer>{
    @Query(value = "select Sum(quantity) from Product_Quantity where product_id = ?1", nativeQuery = true)
    int findSumOfQuantityByProductId(int id);

    ProductQuantity findByProductId(int id);

    @Query(value = "select * from Product_Quantity where product_id = ?1", nativeQuery = true)
    ProductQuantity findByProductIdAndisDisabled(int id);

}
