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
        StringBuilder query = new StringBuilder(
                "select p from Product p inner join p.category pc inner join p.merchant pm inner join p.country py where 1=1");
        Map<String, Object> params = new HashMap<>();
        if (Integer.valueOf(filter.getMerchantId()) != 0) {
            query.append(" and pm.id = :merchant");
            params.put("merchant", filter.getMerchantId());
        }
        if (filter.getOrigin() != null) {
            query.append(" and py.code in :origin");
            params.put("origin", filter.getOrigin());
        }
        if (filter.getStar() != null) {
            query.append(" and p.star in :stars");
            for (Double star : filter.getStar()) {
                if (star != 5) {

                }
            }
            params.put("stars", filter.getStar());
        }
        if (Integer.valueOf(filter.getCategoryId()) != 0) {
            query.append(" and pc.id = :category");
            params.put("category", filter.getCategoryId());
        }
        query.append(" and p.deleted = 0");
        String query1 = "";
        String query2 = "";
        if (filter.getPriceAsc() != null || filter.getPriceDesc() != null) {
            query1 = filter.getPriceAsc() != null ? "order by p.price asc, p.status asc"
                    : "order by p.price desc, p.status asc";
            query.append(filter.getPriceAsc() != null ? " order by p.price asc, p.status asc"
                    : " order by p.price desc, p.status asc");
        } else {
            query2 = "order by p.status asc";
            query.append(" order by p.status asc");
        }
        String queryForCount = query.toString();
        if (filter.getPriceAsc() != null || filter.getPriceDesc() != null) {
            queryForCount = queryForCount.substring(0, queryForCount.length() - 1 - query1.length());
        } else {
            queryForCount = queryForCount.substring(0, queryForCount.length() - 1 - query2.length());
        }
        TypedQuery<Product> tQuery = em.createQuery(query.toString(), Product.class);

        Query countQuery = em.createQuery(queryForCount.replace("select p", "select count(p.id)").trim());
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
