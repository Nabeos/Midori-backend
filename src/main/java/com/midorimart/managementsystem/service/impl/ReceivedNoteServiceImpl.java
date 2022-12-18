package com.midorimart.managementsystem.service.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.midorimart.managementsystem.entity.Product;
import com.midorimart.managementsystem.entity.ProductQuantity;
import com.midorimart.managementsystem.entity.ReceivedNote;
import com.midorimart.managementsystem.entity.ReceivedNoteDetail;
import com.midorimart.managementsystem.exception.custom.CustomBadRequestException;
import com.midorimart.managementsystem.model.CustomError;
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

    // Display inventory receiving voucher with filter
    @Override
    public Map<String, List<ReceivedNoteDTOResponse>> getAllReceivedNote(ReceivedNoteDTOFilter filter) {
        Map<String, List<ReceivedNote>> result = (Map<String, List<ReceivedNote>>) receivedNoteCriteria
                .getAllReceivedNote(filter);
        List<ReceivedNote> receivedNote = result.get("receivedNote");
        return buildDTOListResponse(receivedNote);
    }

    // Display inventory receiving voucher for user
    private Map<String, List<ReceivedNoteDTOResponse>> buildDTOListResponse(List<ReceivedNote> receivedNote) {
        List<ReceivedNoteDTOResponse> receivedNoteDTOResponses = receivedNote.stream()
                .map(ReceivedNoteMapper::toReceivedNoteDTOResponse).collect(Collectors.toList());
        Map<String, List<ReceivedNoteDTOResponse>> wrapper = new HashMap<>();
        wrapper.put("receivedNote", receivedNoteDTOResponses);
        return wrapper;
    }

    // Add new inventory receiving voucher
    @Override
    @Transactional(rollbackFor = { SQLException.class, CustomBadRequestException.class,
            ConstraintViolationException.class, MethodArgumentTypeMismatchException.class,
            HttpMessageNotReadableException.class })
    public Map<String, ReceivedNoteDTOResponse> addNewReceivedNote(
            Map<String, ReceivedNoteDTOCreate> receivedNoteMap) throws CustomBadRequestException {
        ReceivedNoteDTOCreate receivedDTOCreate = receivedNoteMap.get("receivedNote");
        if (receivedDTOCreate.getReceivedDetail() == null || receivedDTOCreate.getReceivedDetail().isEmpty()) {
            throw new CustomBadRequestException(CustomError.builder().code("400").message("Không có sản phẩm").build());
        }
        if (receivedNoteRepository.findByName(receivedDTOCreate.getName()) != null) {
            throw new CustomBadRequestException(CustomError.builder().code("400").message("Bị trùng tên").build());
        }
        ReceivedNote receivedNote = ReceivedNoteMapper.toReceivedNote(receivedDTOCreate);
        receivedNote.setUser(userService.getUserLogin());
        receivedNote.setMerchant(merchantRepository.findById(receivedDTOCreate.getMerchant()).get());
        receivedNote = receivedNoteRepository.save(receivedNote);
        saveReceivedNoteDetail(receivedNote.getReceivedNoteDetail(), receivedNote);
        return buildDTOResponse(receivedNote);
    }

    // Save received note detail
    private void saveReceivedNoteDetail(List<ReceivedNoteDetail> receivedNoteDetailList, ReceivedNote receivedNote)
            throws CustomBadRequestException {
        for (ReceivedNoteDetail receivedNoteDetail : receivedNoteDetailList) {
            Optional<Product> productOptional = productRepository.findById(receivedNoteDetail.getProduct().getId());
            if (!productOptional.isPresent()) {
                throw new CustomBadRequestException(
                        CustomError.builder().code("400").message("Không có sản phẩm").build());
            }
            receivedNoteDetail.setReceivedNote(receivedNote);
            receivedNoteDetail.setProduct(productOptional.get());
            receivedNoteDetail = receivedNoteDetailRepository.save(receivedNoteDetail);
            saveQuantityInStock(receivedNoteDetail, receivedNoteDetail.getProduct());
        }
    }

    // Save quantity of each product to inventory
    private void saveQuantityInStock(ReceivedNoteDetail receivedNoteDetail, Product product) {
        ProductQuantity existedQuantity = productQuantityRepository.findByProductIdAndisDisabled(product.getId());
        // if already had product in database
        if (existedQuantity != null) {
            existedQuantity.setQuantity(receivedNoteDetail.getQuantity() + existedQuantity.getQuantity());
            existedQuantity.setUpdatedDate(new Date());
            existedQuantity.setExpiryDate(receivedNoteDetail.getExpiryDate());
            existedQuantity = productQuantityRepository.save(existedQuantity);
        } else {
            ProductQuantity quantity = new ProductQuantity();
            quantity.setCreatedDate(new Date());
            quantity.setUpdatedDate(new Date());
            quantity.setExpiryDate(receivedNoteDetail.getExpiryDate());
            quantity.setQuantity(receivedNoteDetail.getQuantity());
            quantity.setProduct(product);
            quantity = productQuantityRepository.save(quantity);
        }
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

    // Get inventory receiving voucher by creator
    @Override
    public Map<String, List<ReceivedNoteDTOResponse>> getReceivedNoteByUser(int id, int offset, int limit) {
        List<ReceivedNote> receivedNote = receivedNoteRepository.findByUserId(id, limit, offset);
        return buildDTOListResponse(receivedNote);
    }

    // Get inventory receiving voucher by time ranges
    @Override
    public Map<String, List<ReceivedNoteDTOResponse>> getReceivedNoteByDate(String firstDate, String secondDate,
            int offset, int limit) {
        List<ReceivedNote> receivedNote = receivedNoteRepository.findByDateRange(firstDate + " 00:00:00",
                secondDate + " 23:59:59", limit, offset);
        return buildDTOListResponse(receivedNote);
    }

    // Get inventory receiving voucher by merchant
    @Override
    public Map<String, List<ReceivedNoteDTOResponse>> getReceivedNoteByMerchant(int id, int offset, int limit) {
        List<ReceivedNote> receivedNote = receivedNoteRepository.findByMerchantId(id, limit, offset);
        return buildDTOListResponse(receivedNote);
    }

}
