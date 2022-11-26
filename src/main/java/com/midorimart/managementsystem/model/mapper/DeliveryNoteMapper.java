package com.midorimart.managementsystem.model.mapper;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.midorimart.managementsystem.entity.DeliveryNote;
import com.midorimart.managementsystem.entity.DeliveryNoteDetail;
import com.midorimart.managementsystem.entity.Order;
import com.midorimart.managementsystem.entity.Product;
import com.midorimart.managementsystem.model.deliveryNote.DeliveryDetailDTOCreate;
import com.midorimart.managementsystem.model.deliveryNote.DeliveryDetailDTOResponse;
import com.midorimart.managementsystem.model.deliveryNote.DeliveryNoteDTOCreate;
import com.midorimart.managementsystem.model.deliveryNote.DeliveryNoteDTOResponse;
import com.midorimart.managementsystem.model.deliveryNote.DeliveryOrderDTO;
import com.midorimart.managementsystem.utils.DateHelper;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DeliveryNoteMapper {
    public static DeliveryNote toDeliveryNote(DeliveryNoteDTOCreate receivedDTOCreate) {
        return DeliveryNote.builder()
                .name(receivedDTOCreate.getName())
                .createdAt(new Date())
                .status(1)
                .note(receivedDTOCreate.getNote())
                .deliveryNoteDetails(toListDeliveryNoteDetail(receivedDTOCreate.getDeliveryDetail()))
                .build();
    }

    private static List<DeliveryNoteDetail> toListDeliveryNoteDetail(List<DeliveryDetailDTOCreate> receivedDetail) {
        return receivedDetail.stream().map(DeliveryNoteMapper::toDeliveryNoteDetail).collect(Collectors.toList());
    }

    private static List<DeliveryDetailDTOResponse> toListDeliveryNoteDetailDTOResponses(List<DeliveryNoteDetail> receivedDetail) {
        return receivedDetail.stream().map(DeliveryNoteMapper::toDeliveryNoteDetailDTOResponse).collect(Collectors.toList());
    }

    private static DeliveryNoteDetail toDeliveryNoteDetail(DeliveryDetailDTOCreate deliveryDetailDTOCreate) {
        return DeliveryNoteDetail.builder()
                .product(Product.builder().id(deliveryDetailDTOCreate.getProductId()).build())
                .quantity(deliveryDetailDTOCreate.getQuantityExport())
                .expiryDate(deliveryDetailDTOCreate.getExpiryDate())
                .price(deliveryDetailDTOCreate.getPrice())
                .totalPrice(deliveryDetailDTOCreate.getTotalPrice())
                .build();
    }

    private static DeliveryDetailDTOResponse toDeliveryNoteDetailDTOResponse(DeliveryNoteDetail receivedDetailDTO) {
        return DeliveryDetailDTOResponse.builder()
                .id(receivedDetailDTO.getProduct().getId())
                .title(receivedDetailDTO.getProduct().getTitle())
                .sku(receivedDetailDTO.getProduct().getSku())
                .quantityExport(receivedDetailDTO.getQuantity())
                .price(receivedDetailDTO.getPrice())
                .totalPrice(receivedDetailDTO.getTotalPrice())
                .build();
    }

    public static DeliveryNoteDTOResponse toDeliveryNoteDTOResponse(DeliveryNote DeliveryNote) {
        return DeliveryNoteDTOResponse.builder()
        .id(DeliveryNote.getId())
        .name(DeliveryNote.getName())
        .note(DeliveryNote.getNote())
        .order(toDeliveryOrderDTO(DeliveryNote.getOrder()))
        .createdAt(DateHelper.convertDate(DeliveryNote.getCreatedAt()))
        .createdBy(DeliveryNote.getUser().getFullname())
        .status(DeliveryNote.getStatus())
        .deliveryDetail(toListDeliveryNoteDetailDTOResponses(DeliveryNote.getDeliveryNoteDetails()))
        .build();
    }

    private static DeliveryOrderDTO toDeliveryOrderDTO(Order order) {
        return DeliveryOrderDTO.builder()
        .id(order.getId())
        .orderDate(DateHelper.convertDate(order.getOrderDate()))
        .orderNumber(order.getOrderNumber())
        .receiveProductsMethod(getReceiveMethod(order.getReceiveProductsMethod()))
        .totalBill(order.getTotalMoney())
        .build();
    }
    public static String getReceiveMethod(int receive) {
        switch (receive) {
            case 1:
                return "Giao Tận Nhà";
            default:
                return "Nhận Tại Cửa Hàng";
        }
    }
}
