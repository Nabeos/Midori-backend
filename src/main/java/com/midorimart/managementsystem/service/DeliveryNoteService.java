package com.midorimart.managementsystem.service;

import java.util.List;
import java.util.Map;

import com.midorimart.managementsystem.model.deliveryNote.DeliveryNoteDTOFilter;
import com.midorimart.managementsystem.model.deliveryNote.DeliveryNoteDTOResponse;

public interface DeliveryNoteService {

    Map<String, String> deleteDeliveryNote(int id);

    // Map<String, DeliveryNoteDTOResponse> addNewDeliveryNote(Map<String, DeliveryNoteDTOCreate> deliveryNoteMap);

    Map<String, List<DeliveryNoteDTOResponse>> getDeliveryNoteByDate(String firstDate, String secondDate, int limit,
            int offset);

    Map<String, List<DeliveryNoteDTOResponse>> getDeliveryNoteByUser(int id, int limit, int offset);

    Map<String, List<DeliveryNoteDTOResponse>> getAllDeliveryNote(DeliveryNoteDTOFilter filter);

}
