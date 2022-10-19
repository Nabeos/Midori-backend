package com.midorimart.managementsystem.repository.custom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.midorimart.managementsystem.entity.Product;
import com.midorimart.managementsystem.model.product.dto.ProductDTOFilter;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductCriteria {
    private final EntityManager em;

    public Map<String, Object> getProductList(ProductDTOFilter filter) {
        StringBuilder query = new StringBuilder("select p from Product p inner join p.category pc where 1=1");
        Map<String, Object> params = new HashMap<>();
        if (Integer.valueOf(filter.getCategoryId()) != 0) {
            query.append(" and pc.id = :category");
            params.put("category", filter.getCategoryId());
        }

        TypedQuery<Product> tQuery = em.createQuery(query.toString(), Product.class);
        Query countQuery = em.createQuery(query.toString().replace("select p", "select count(p.id)"));
        params.forEach((k, v) -> {
            tQuery.setParameter(k, v);
            countQuery.setParameter(k, v);
        });
        tQuery.setFirstResult(filter.getOffset());
        tQuery.setMaxResults(filter.getLimit());

        // Get product after query
        List<Product> listProducts = tQuery.getResultList();
        Long totalProducts = (Long) countQuery.getSingleResult();

        Map<String, Object> results = new HashMap<>();
        results.put("listProducts", listProducts);
        results.put("totalProducts", totalProducts);
        return results;
    }
}
