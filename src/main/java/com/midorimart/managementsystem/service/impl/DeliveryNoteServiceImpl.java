package com.midorimart.managementsystem.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.midorimart.managementsystem.entity.DeliveryNote;
import com.midorimart.managementsystem.entity.DeliveryNoteDetail;
import com.midorimart.managementsystem.entity.OrderDetail;
import com.midorimart.managementsystem.entity.ProductQuantity;
import com.midorimart.managementsystem.model.deliveryNote.DeliveryNoteDTOFilter;
import com.midorimart.managementsystem.model.deliveryNote.DeliveryNoteDTOResponse;
import com.midorimart.managementsystem.model.mapper.DeliveryNoteMapper;
import com.midorimart.managementsystem.repository.DeliveryNoteDetailRepository;
import com.midorimart.managementsystem.repository.DeliveryNoteRepository;
import com.midorimart.managementsystem.repository.OrderRepository;
import com.midorimart.managementsystem.repository.ProductQuantityRepository;
import com.midorimart.managementsystem.repository.ProductRepository;
import com.midorimart.managementsystem.repository.custom.DeliveryNoteCriteria;
import com.midorimart.managementsystem.service.DeliveryNoteService;
import com.midorimart.managementsystem.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryNoteServiceImpl implements DeliveryNoteService {
    private final DeliveryNoteRepository deliveryNoteRepository;
    private final DeliveryNoteDetailRepository deliveryNoteDetailRepository;
    private final ProductRepository productRepository;
    private final ProductQuantityRepository productQuantityRepository;
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final DeliveryNoteCriteria deliveryNoteCriteria;

    @Override
    public Map<String, String> deleteDeliveryNote(int id) {
        DeliveryNote deliveryNote = deliveryNoteRepository.findById(id).get();
        deliveryNote.setStatus(0);
        deliveryNote = deliveryNoteRepository.save(deliveryNote);
        Map<String, String> wrapper = new HashMap<>();
        wrapper.put("deliveryNote", "Delete successfully");
        return wrapper;
    }

    public DeliveryNote addNewDeliveryNote(DeliveryNote deliveryNote, List<OrderDetail> cart) {
        deliveryNote = deliveryNoteRepository.save(deliveryNote);
        saveDeliveryNoteDetail(cart, deliveryNote);
        return deliveryNote;
    }

    @Override
    public Map<String, List<DeliveryNoteDTOResponse>> getDeliveryNoteByDate(String firstDate, String secondDate,
            int limit, int offset) {
        return null;
    }

    @Override
    public Map<String, List<DeliveryNoteDTOResponse>> getDeliveryNoteByUser(int id, int limit, int offset) {
        return null;
    }

    @Override
    public Map<String, List<DeliveryNoteDTOResponse>> getAllDeliveryNote(DeliveryNoteDTOFilter filter) {
        Map<String, List<DeliveryNote>> result = (Map<String, List<DeliveryNote>>) deliveryNoteCriteria
                .getAllDeliveryNote(filter);
        List<DeliveryNote> deliveryNotes = result.get("deliveryNotes");
        return buildDTOListResponse(deliveryNotes);
    }

    private Map<String, List<DeliveryNoteDTOResponse>> buildDTOListResponse(List<DeliveryNote> deliveryNotes) {
        List<DeliveryNoteDTOResponse> receivedNoteDTOResponses = deliveryNotes.stream()
                .map(DeliveryNoteMapper::toDeliveryNoteDTOResponse).collect(Collectors.toList());
        Map<String, List<DeliveryNoteDTOResponse>> wrapper = new HashMap<>();
        wrapper.put("deliveryNotes", receivedNoteDTOResponses);
        return wrapper;
    }

    private Map<String, DeliveryNoteDTOResponse> buildDTOResponse(DeliveryNote deliveryNote) {
        DeliveryNoteDTOResponse deliveryNoteDTOResponse = DeliveryNoteMapper.toDeliveryNoteDTOResponse(deliveryNote);
        Map<String, DeliveryNoteDTOResponse> wrapper = new HashMap<>();
        wrapper.put("deliveryNote", deliveryNoteDTOResponse);
        return wrapper;
    }

    private void saveDeliveryNoteDetail(List<OrderDetail> carts, DeliveryNote deliveryNote) {
        for (OrderDetail cart : carts) {
            DeliveryNoteDetail deliveryNoteDetail = new DeliveryNoteDetail();
            deliveryNoteDetail.setQuantity(cart.getQuantity());
            deliveryNoteDetail.setPrice(cart.getPrice());
            deliveryNoteDetail.setTotalPrice(cart.getTotalMoney());
            deliveryNoteDetail.setDeliveryNote(deliveryNote);
            deliveryNoteDetail.setProduct(productRepository.findById(cart.getProduct().getId()).get());
            deliveryNoteDetail = deliveryNoteDetailRepository.save(deliveryNoteDetail);
            reduceQuantityInStock(deliveryNoteDetail, deliveryNoteDetail.getProduct().getId());
        }
    }

    private void reduceQuantityInStock(DeliveryNoteDetail deliveryNoteDetail, int id) {
        ProductQuantity existedQuantity = productQuantityRepository.findByProductIdAndisDisabled(id);
        existedQuantity.setQuantity(existedQuantity.getQuantity() - deliveryNoteDetail.getQuantity());
        existedQuantity.setUpdatedDate(new Date());
        existedQuantity = productQuantityRepository.save(existedQuantity);
    }
}
