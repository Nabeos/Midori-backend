package com.midorimart.managementsystem.service;

import java.util.List;
import java.util.Map;

import com.midorimart.managementsystem.model.receivedNote.ReceivedNoteDTOCreate;
import com.midorimart.managementsystem.model.receivedNote.ReceivedNoteDTOResponse;

public interface ReceivedNoteService {

    Map<String, List<ReceivedNoteDTOResponse>> getAllReceivedNote(int offset, int limit);

    Map<String, ReceivedNoteDTOResponse> addNewReceivedNote(Map<String, ReceivedNoteDTOCreate> receivedNoteMap);

    // Map<String, ReceivedNoteDTOResponse> updateReceivedNote(Map<String, ReceivedNoteDTOCreate> receivedNoteMap,
            // int id);

    Map<String, String> deleteReceivedNote(int id);

    Map<String, List<ReceivedNoteDTOResponse>> getReceivedNoteByUser(int id, int limit, int offset);

    Map<String, List<ReceivedNoteDTOResponse>> getReceivedNoteByDate(String firstDate, String secondDate, int limit, int offset);

    Map<String, List<ReceivedNoteDTOResponse>> getReceivedNoteByMerchant(int id, int limit, int offset);

}
