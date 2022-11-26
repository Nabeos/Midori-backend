package com.midorimart.managementsystem.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.midorimart.managementsystem.model.deliveryNote.DeliveryNoteDTOFilter;
import com.midorimart.managementsystem.model.deliveryNote.DeliveryNoteDTOResponse;
import com.midorimart.managementsystem.service.DeliveryNoteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@Tag(name = "Delivery Note API")
public class DeliveryNoteController {
    private final DeliveryNoteService DeliveryNoteService;

    @Operation(summary = "Get All Delivery note")
    @GetMapping("/api/v1/delivery-notes")
    public Map<String, List<DeliveryNoteDTOResponse>> getAllDeliveryNote(
            @RequestParam(name = "user", required = true, defaultValue = "0") int userId,
            @RequestParam(name = "firstDate", required = false) String firstDate,
            @RequestParam(name = "secondDate", required = false) String secondDate,
            @RequestParam(name = "merchant", defaultValue = "-1", required = true) int orderId,
            @RequestParam(name = "offset", required = true, defaultValue = "0") int offset,
            @RequestParam(name = "limit", required = true, defaultValue = "20") int limit) {
        DeliveryNoteDTOFilter filter = DeliveryNoteDTOFilter.builder().userId(userId).firstDate(firstDate)
                .secondDate(secondDate).orderId(orderId).offset(offset).limit(limit).build();
        return DeliveryNoteService.getAllDeliveryNote(filter);
    }

    @Operation(summary = "Search Delivery note By User")
    @GetMapping("/api/v1/delivery-notes/users")
    public Map<String, List<DeliveryNoteDTOResponse>> getDeliveryNoteByUser(
            @RequestParam(name = "id", required = true) int id,
            @RequestParam(name = "offset", required = true, defaultValue = "0") int offset,
            @RequestParam(name = "limit", required = true, defaultValue = "20") int limit) {
        return DeliveryNoteService.getDeliveryNoteByUser(id, limit, offset);
    }

    @Operation(summary = "Search Delivery note By Date Range")
    @GetMapping("/api/v1/delivery-notes/range")
    public Map<String, List<DeliveryNoteDTOResponse>> getDeliveryNoteByDate(
            @RequestParam(name = "firstDate") String firstDate, @RequestParam(name = "secondDate") String secondDate,
            @RequestParam(name = "offset", required = true, defaultValue = "0") int offset,
            @RequestParam(name = "limit", required = true, defaultValue = "20") int limit) {
        return DeliveryNoteService.getDeliveryNoteByDate(firstDate, secondDate, limit, offset);
    }

    // @Operation(summary = "Create new Delivery note")
    // @PostMapping("/api/v1/delivery-notes")
    // public Map<String, DeliveryNoteDTOResponse> addNewDeliveryNote(
    //         @RequestBody Map<String, DeliveryNoteDTOCreate> DeliveryNoteMap) {
    //     return DeliveryNoteService.addNewDeliveryNote(DeliveryNoteMap);
    // }

    @Operation(summary = "Delete Delivery note")
    @PutMapping("/api/v1/delivery-notes")
    public Map<String, String> deleteDeliveryNote(
            @RequestParam(name = "id", required = true, defaultValue = "0") int id) {
        return DeliveryNoteService.deleteDeliveryNote(id);
    }
}
