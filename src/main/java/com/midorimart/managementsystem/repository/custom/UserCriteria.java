package com.midorimart.managementsystem.repository.custom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.midorimart.managementsystem.entity.User;
import com.midorimart.managementsystem.model.users.UserDTOFilter;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserCriteria {
    private final EntityManager em;

    public Map<String, Object> getAllUsers(UserDTOFilter filter){
        StringBuilder query = new StringBuilder("select u from User u where 1 = 1");
        Map<String, Object> params = new HashMap<>();
        TypedQuery<User> typedQuery = em.createQuery(query.toString(), User.class);

        Query countQuery = em.createQuery(query.toString().replace("select u", "select count(u.id)"));
        typedQuery.setMaxResults(filter.getLimit());
        typedQuery.setFirstResult(filter.getOffset());

        List<User> users = typedQuery.getResultList();
        Long totalUsers = (Long) countQuery.getSingleResult();
        Map<String, Object> results = new HashMap<>();
        results.put("users", users);
        results.put("totalUsers", totalUsers);
        return results;
    }
}
