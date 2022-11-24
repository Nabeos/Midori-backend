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

import com.midorimart.managementsystem.entity.ReceivedNote;
import com.midorimart.managementsystem.model.receivedNote.ReceivedNoteDTOFilter;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReceivedNoteCriteria {
    private final EntityManager em;

    public Map<String, List<ReceivedNote>> getAllReceivedNote(ReceivedNoteDTOFilter filter) {
        StringBuilder query = new StringBuilder(
                "select rn from ReceivedNote rn inner join rn.user ru inner join rn.merchant rm where 1=1");
        Map<String, Object> params = new HashMap<>();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date firstDate = null;
        Date secondDate = null;
        if (filter.getUserId() != 0) {
            query.append(" and ru.id = :userId");
            params.put("userId", filter.getUserId());
        }
        if (filter.getMerchantId() >= 0) {
            query.append(" and rm.id = :merchantId");
            params.put("merchantId", filter.getMerchantId());
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

        TypedQuery<ReceivedNote> tQuery = em.createQuery(query.toString(), ReceivedNote.class);
        params.forEach((k, v) -> {
            tQuery.setParameter(k, v);
        });
        tQuery.setMaxResults(filter.getLimit());
        tQuery.setFirstResult(filter.getOffset());
        List<ReceivedNote> receivedNotes = tQuery.getResultList();
        Map<String, List<ReceivedNote>> result = new HashMap<>();
        result.put("receivedNote", receivedNotes);
        return result;
    }
}
