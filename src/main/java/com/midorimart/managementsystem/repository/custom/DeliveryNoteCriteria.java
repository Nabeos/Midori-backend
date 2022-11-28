package com.midorimart.managementsystem.repository.custom;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.midorimart.managementsystem.entity.DeliveryNote;
import com.midorimart.managementsystem.model.deliveryNote.DeliveryNoteDTOFilter;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DeliveryNoteCriteria {
    private final EntityManager em;

    public Map<String, List<DeliveryNote>> getAllDeliveryNote(DeliveryNoteDTOFilter filter) {
        StringBuilder query = new StringBuilder(
                "select rn from DeliveryNote rn inner join rn.user ru inner join rn.order rm where 1=1");
        Map<String, Object> params = new HashMap<>();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date firstDate = null;
        Date secondDate = null;
        if (filter.getUserId() != 0) {
            query.append(" and ru.id = :userId");
            params.put("userId", filter.getUserId());
        }
        if (filter.getOrderId() >= 0) {
            query.append(" and rm.id = :orderId");
            params.put("orderId", filter.getOrderId());
        }
        if (filter.getFirstDate() != null && filter.getSecondDate() != null) {
            try {
                firstDate = df.parse(filter.getFirstDate()+" 00:00:00");
                secondDate = df.parse(filter.getSecondDate()+ " 23:59:59");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            query.append(" and rn.createdAt between :firstDate and :secondDate");
            params.put("firstDate", firstDate);
            params.put("secondDate", secondDate);
        }
        query.append(" and rn.status = 1 order by rn.createdAt desc");
        TypedQuery<DeliveryNote> tQuery = em.createQuery(query.toString(), DeliveryNote.class);
        params.forEach((k, v) -> {
            tQuery.setParameter(k, v);
        });
        tQuery.setMaxResults(filter.getLimit());
        tQuery.setFirstResult(filter.getOffset());
        List<DeliveryNote> deliveryNotes = tQuery.getResultList();
        Map<String, List<DeliveryNote>> result = new HashMap<>();
        result.put("deliveryNotes", deliveryNotes);
        return result;
    }
}
