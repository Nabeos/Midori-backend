package com.midorimart.managementsystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.midorimart.managementsystem.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    Optional<Order> findByOrderNumber(String orderNumber);

    @Query(value = "select *, datediff(DAY, delivery_date, GETDATE()) as date_diff from [Order] where status = 0 and delivery_date >= DATEADD(dd, 0, DATEDIFF(dd, 0, GETDATE())) order by date_diff desc", nativeQuery = true)
    List<Order> findOrderByDeliveryDate();
}
