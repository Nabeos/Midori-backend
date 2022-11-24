package com.midorimart.managementsystem.service.impl;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.midorimart.managementsystem.entity.Product;
import com.midorimart.managementsystem.entity.ProductQuantity;
import com.midorimart.managementsystem.entity.ReceivedNote;
import com.midorimart.managementsystem.entity.ReceivedNoteDetail;
import com.midorimart.managementsystem.model.mapper.ReceivedNoteMapper;
import com.midorimart.managementsystem.model.receivedNote.ReceivedNoteDTOCreate;
import com.midorimart.managementsystem.model.receivedNote.ReceivedNoteDTOFilter;
import com.midorimart.managementsystem.model.receivedNote.ReceivedNoteDTOResponse;
import com.midorimart.managementsystem.repository.MerchantRepository;
import com.midorimart.managementsystem.repository.ProductQuantityRepository;
import com.midorimart.managementsystem.repository.ProductRepository;
import com.midorimart.managementsystem.repository.ReceivedNoteDetailRepository;
import com.midorimart.managementsystem.repository.ReceivedNoteRepository;
import com.midorimart.managementsystem.repository.custom.ReceivedNoteCriteria;
import com.midorimart.managementsystem.service.ReceivedNoteService;
import com.midorimart.managementsystem.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReceivedNoteServiceImpl implements ReceivedNoteService {
    private final ReceivedNoteRepository receivedNoteRepository;
    private final ReceivedNoteDetailRepository receivedNoteDetailRepository;
    private final ReceivedNoteCriteria receivedNoteCriteria;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final MerchantRepository merchantRepository;
    private final ProductQuantityRepository productQuantityRepository;

    @Override
    public Map<String, List<ReceivedNoteDTOResponse>> getAllReceivedNote(ReceivedNoteDTOFilter filter) {
        Map<String, List<ReceivedNote>> result = (Map<String, List<ReceivedNote>>) receivedNoteCriteria.getAllReceivedNote(filter);
        List<ReceivedNote> receivedNote = result.get("receivedNote");
        return buildDTOListResponse(receivedNote);
    }

    private Map<String, List<ReceivedNoteDTOResponse>> buildDTOListResponse(List<ReceivedNote> receivedNote) {
        List<ReceivedNoteDTOResponse> receivedNoteDTOResponses = receivedNote.stream()
                .map(ReceivedNoteMapper::toReceivedNoteDTOResponse).collect(Collectors.toList());
        Map<String, List<ReceivedNoteDTOResponse>> wrapper = new HashMap<>();
        wrapper.put("receivedNote", receivedNoteDTOResponses);
        return wrapper;
    }

    @Override
    @Transactional(rollbackFor = SQLException.class)
    public Map<String, ReceivedNoteDTOResponse> addNewReceivedNote(
            Map<String, ReceivedNoteDTOCreate> receivedNoteMap) {
        ReceivedNoteDTOCreate receivedDTOCreate = receivedNoteMap.get("receivedNote");
        ReceivedNote receivedNote = ReceivedNoteMapper.toReceivedNote(receivedDTOCreate);
        receivedNote.setUser(userService.getUserLogin());
        receivedNote.setMerchant(merchantRepository.findById(receivedDTOCreate.getMerchant()).get());
        receivedNote = receivedNoteRepository.save(receivedNote);
        saveReceivedNoteDetail(receivedNote.getReceivedNoteDetail(), receivedNote);
        return buildDTOResponse(receivedNote);
    }

    // @Override
    // public Map<String, ReceivedNoteDTOResponse> updateReceivedNote(Map<String, ReceivedNoteDTOCreate> receivedNoteMap,
    //         int id) {
    //     ReceivedNoteDTOCreate receivedNoteDTOCreate = receivedNoteMap.get("receivedNote");
    //     ReceivedNote updateNote = ReceivedNoteMapper.toReceivedNote(receivedNoteDTOCreate);
    //     ReceivedNote existedNote = receivedNoteRepository.findById(id).get();
    //     existedNote.setUser(userService.getUserLogin());
    //     existedNote.setName(receivedNoteDTOCreate.getName());
    //     existedNote.setNote(receivedNoteDTOCreate.getNote());
    //     existedNote.setMerchant(merchantRepository.findById(receivedNoteDTOCreate.getMerchant()).get());
    //     existedNote.setReceivedNoteDetail(updateNote.getReceivedNoteDetail());
    //     existedNote = receivedNoteRepository.save(existedNote);
    //     return buildDTOResponse(existedNote);
    // }

    private void saveReceivedNoteDetail(List<ReceivedNoteDetail> receivedNoteDetailList, ReceivedNote receivedNote) {
        for (ReceivedNoteDetail receivedNoteDetail : receivedNoteDetailList) {
            receivedNoteDetail.setReceivedNote(receivedNote);
            receivedNoteDetail.setProduct(productRepository.findById(receivedNoteDetail.getProduct().getId()).get());
            receivedNoteDetail = receivedNoteDetailRepository.save(receivedNoteDetail);
            saveQuantityInStock(receivedNoteDetail, receivedNoteDetail.getProduct());
        }
    }

    private void saveQuantityInStock(ReceivedNoteDetail receivedNoteDetail, Product product) {
        ProductQuantity productQuantity = new ProductQuantity();
        productQuantity.setQuantity(receivedNoteDetail.getQuantity());
        productQuantity.setProduct(product);
        productQuantity.setExpiryDate(receivedNoteDetail.getExpiryDate());
        productQuantity = productQuantityRepository.save(productQuantity);
    }

    private Map<String, ReceivedNoteDTOResponse> buildDTOResponse(ReceivedNote receivedNote) {
        ReceivedNoteDTOResponse receivedNoteDTOResponse = ReceivedNoteMapper.toReceivedNoteDTOResponse(receivedNote);
        Map<String, ReceivedNoteDTOResponse> wrapper = new HashMap<>();
        wrapper.put("receivedNote", receivedNoteDTOResponse);
        return wrapper;
    }

    @Override
    public Map<String, String> deleteReceivedNote(int id) {
        ReceivedNote existedNote = receivedNoteRepository.findById(id).get();
        existedNote.setStatus(0);
        receivedNoteRepository.save(existedNote);
        Map<String, String> wrapper = new HashMap<>();
        wrapper.put("receivedNote", "delete successfully");
        return wrapper;
    }

    @Override
    public Map<String, List<ReceivedNoteDTOResponse>> getReceivedNoteByUser(int id, int offset, int limit) {
        List<ReceivedNote> receivedNote = receivedNoteRepository.findByUserId(id, limit, offset);
        return buildDTOListResponse(receivedNote);
    }

    @Override
    public Map<String, List<ReceivedNoteDTOResponse>> getReceivedNoteByDate(String firstDate, String secondDate, int offset, int limit) {
        List<ReceivedNote> receivedNote = receivedNoteRepository.findByDateRange(firstDate+" 00:00:00", secondDate+" 00:00:00", limit, offset);
        return buildDTOListResponse(receivedNote);
    }

    @Override
    public Map<String, List<ReceivedNoteDTOResponse>> getReceivedNoteByMerchant(int id, int offset, int limit) {
        List<ReceivedNote> receivedNote = receivedNoteRepository.findByMerchantId(id, limit, offset);
        return buildDTOListResponse(receivedNote);
    }

}
