package com.midorimart.managementsystem.repository.custom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Repository;

import com.midorimart.managementsystem.entity.Order;
import com.midorimart.managementsystem.model.order.OrderDTOFilter;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderCriteria {
    private final EntityManager em;

    public Map<String, Object> getOrders(OrderDTOFilter filter) {
        StringBuilder queryOrder = new StringBuilder("select o from Order o where status = :status");
        Map<String, Object> param = new HashMap<>();
        param.put("status", filter.getStatus());
        TypedQuery<Order> tQuery = em.createQuery(queryOrder.toString(), Order.class);


        param.forEach((k, v) -> {
            tQuery.setParameter(k, v);
        });
        tQuery.setMaxResults(filter.getLimit());
        tQuery.setFirstResult(filter.getOffset());
        List<Order> orders = tQuery.getResultList();
        Map<String, Object> result = new HashMap<>();
        result.put("totalOrders", orders);
        return result;
    }
}
