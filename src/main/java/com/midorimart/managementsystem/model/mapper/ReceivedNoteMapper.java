package com.midorimart.managementsystem.model.mapper;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.midorimart.managementsystem.entity.Merchant;
import com.midorimart.managementsystem.entity.Product;
import com.midorimart.managementsystem.entity.ReceivedNote;
import com.midorimart.managementsystem.entity.ReceivedNoteDetail;
import com.midorimart.managementsystem.model.receivedNote.ReceivedDetailDTOCreate;
import com.midorimart.managementsystem.model.receivedNote.ReceivedDetailDTOResponse;
import com.midorimart.managementsystem.model.receivedNote.ReceivedNoteDTOCreate;
import com.midorimart.managementsystem.model.receivedNote.ReceivedNoteDTOResponse;
import com.midorimart.managementsystem.utils.DateHelper;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ReceivedNoteMapper {
    public static ReceivedNote toReceivedNote(ReceivedNoteDTOCreate receivedDTOCreate) {
        return ReceivedNote.builder()
                .name(receivedDTOCreate.getName())
                .createdAt(new Date())
                .status(1)
                .note(receivedDTOCreate.getNote())
                .receivedNoteDetail(toListReceivedNoteDetail(receivedDTOCreate.getReceivedDetail()))
                .build();
    }

    private static List<ReceivedNoteDetail> toListReceivedNoteDetail(List<ReceivedDetailDTOCreate> receivedDetail) {
        return receivedDetail.stream().map(ReceivedNoteMapper::toReceivedNoteDetail).collect(Collectors.toList());
    }

    private static List<ReceivedDetailDTOResponse> toListReceivedNoteDetailDTOResponses(List<ReceivedNoteDetail> receivedDetail) {
        return receivedDetail.stream().map(ReceivedNoteMapper::toReceivedNoteDetailDTOResponse).collect(Collectors.toList());
    }

    private static ReceivedNoteDetail toReceivedNoteDetail(ReceivedDetailDTOCreate receivedDetailDTOCreate) {
        return ReceivedNoteDetail.builder()
                .product(Product.builder().id(receivedDetailDTOCreate.getProductId()).build())
                .quantity(receivedDetailDTOCreate.getQuantityImport())
                .expiryDate(receivedDetailDTOCreate.getExpiryDate())
                .price(receivedDetailDTOCreate.getPrice())
                .totalPrice(receivedDetailDTOCreate.getTotalPrice())
                .build();
    }

    private static ReceivedDetailDTOResponse toReceivedNoteDetailDTOResponse(ReceivedNoteDetail receivedDetailDTO) {
        return ReceivedDetailDTOResponse.builder()
                .id(receivedDetailDTO.getProduct().getId())
                .title(receivedDetailDTO.getProduct().getTitle())
                .sku(receivedDetailDTO.getProduct().getSku())
                .quantityImport(receivedDetailDTO.getQuantity())
                .expiryDate(DateHelper.convertDate(receivedDetailDTO.getExpiryDate()))
                .price(receivedDetailDTO.getPrice())
                .totalPrice(receivedDetailDTO.getTotalPrice())
                .build();
    }

    public static ReceivedNoteDTOResponse toReceivedNoteDTOResponse(ReceivedNote receivedNote) {
        return ReceivedNoteDTOResponse.builder()
        .id(receivedNote.getId())
        .name(receivedNote.getName())
        .note(receivedNote.getNote())
        .createdAt(DateHelper.convertDate(receivedNote.getCreatedAt()))
        .createdBy(receivedNote.getUser().getFullname())
        .merchant(MerchantMapper.toMerchantDTOResponse(receivedNote.getMerchant()))
        .status(receivedNote.getStatus())
        .receivedDetail(toListReceivedNoteDetailDTOResponses(receivedNote.getReceivedNoteDetail()))
        .build();
    }

}
