package com.midorimart.managementsystem.model.deliveryNote;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DeliveryNoteDTOResponse {
    private int id;
    private String name;
    private String createdAt;
    private String createdBy;
    private String note;
    private DeliveryOrderDTO order;
    private int status;
    private List<DeliveryDetailDTOResponse> deliveryDetail;
}
