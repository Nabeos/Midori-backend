package com.midorimart.managementsystem.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.midorimart.managementsystem.model.receivedNote.ReceivedNoteDTOCreate;
import com.midorimart.managementsystem.model.receivedNote.ReceivedNoteDTOFilter;
import com.midorimart.managementsystem.model.receivedNote.ReceivedNoteDTOResponse;
import com.midorimart.managementsystem.service.ReceivedNoteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@Tag(name = "Received Note API")
public class ReceivedNoteController {
    private final ReceivedNoteService receivedNoteService;

    @Operation(summary = "Get All received note")
    @GetMapping("/api/v1/received-notes")
    public Map<String, List<ReceivedNoteDTOResponse>> getAllReceivedNote(
            @RequestParam(name = "user", required = true, defaultValue = "0") int userId,
            @RequestParam(name = "firstDate", required = false) String firstDate,
            @RequestParam(name = "secondDate", required = false) String secondDate,
            @RequestParam(name = "merchant", defaultValue = "-1", required = true) int merchantId,
            @RequestParam(name = "offset", required = true, defaultValue = "0") int offset,
            @RequestParam(name = "limit", required = true, defaultValue = "20") int limit) throws ParseException {
        ReceivedNoteDTOFilter filter = ReceivedNoteDTOFilter.builder().userId(userId).firstDate(firstDate)
                .secondDate(secondDate).merchantId(merchantId).offset(offset).limit(limit).build();
        return receivedNoteService.getAllReceivedNote(filter);
    }

    @Operation(summary = "Search Received note By User")
    @GetMapping("/api/v1/received-notes/users")
    public Map<String, List<ReceivedNoteDTOResponse>> getReceivedNoteByUser(
            @RequestParam(name = "id", required = true) int id,
            @RequestParam(name = "offset", required = true, defaultValue = "0") int offset,
            @RequestParam(name = "limit", required = true, defaultValue = "20") int limit) {
        return receivedNoteService.getReceivedNoteByUser(id, limit, offset);
    }

    @Operation(summary = "Search Received note By Month")
    @GetMapping("/api/v1/received-notes/month")
    public Map<String, List<ReceivedNoteDTOResponse>> getReceivedNoteByMonth(@RequestParam(name = "month") int month,
            @RequestParam(name = "offset", required = true, defaultValue = "0") int offset,
            @RequestParam(name = "limit", required = true, defaultValue = "20") int limit) {
        return receivedNoteService.getReceivedNoteByUser(month, limit, offset);
    }

    @Operation(summary = "Search Received note By Date Range")
    @GetMapping("/api/v1/received-notes/range")
    public Map<String, List<ReceivedNoteDTOResponse>> getReceivedNoteByDate(
            @RequestParam(name = "firstDate") String firstDate, @RequestParam(name = "secondDate") String secondDate,
            @RequestParam(name = "offset", required = true, defaultValue = "0") int offset,
            @RequestParam(name = "limit", required = true, defaultValue = "20") int limit) {
        return receivedNoteService.getReceivedNoteByDate(firstDate, secondDate, limit, offset);
    }

    @Operation(summary = "Get Received note By Merchant")
    @GetMapping("/api/v1/received-notes/merchant")
    public Map<String, List<ReceivedNoteDTOResponse>> getReceivedNoteByMerchant(
            @RequestParam(name = "id") int id,
            @RequestParam(name = "offset", required = true, defaultValue = "0") int offset,
            @RequestParam(name = "limit", required = true, defaultValue = "20") int limit) {
        return receivedNoteService.getReceivedNoteByMerchant(id, limit, offset);
    }

    @Operation(summary = "Create new received note")
    @PostMapping("/api/v1/received-notes")
    public Map<String, ReceivedNoteDTOResponse> addNewReceivedNote(
            @RequestBody Map<String, ReceivedNoteDTOCreate> receivedNoteMap) {
        return receivedNoteService.addNewReceivedNote(receivedNoteMap);
    }

    // @Operation(summary = "Update received note")
    // @PutMapping("/api/v1/received-notes/{id}")
    // public Map<String, ReceivedNoteDTOResponse> updateReceivedNote(@RequestBody
    // Map<String, ReceivedNoteDTOCreate> receivedNoteMap, @PathVariable int id) {
    // return receivedNoteService.updateReceivedNote(receivedNoteMap, id);
    // }

    @Operation(summary = "Delete received note")
    @PutMapping("/api/v1/received-notes")
    public Map<String, String> deleteReceivedNote(
            @RequestParam(name = "id", required = true, defaultValue = "0") int id) {
        return receivedNoteService.deleteReceivedNote(id);
    }
}
