package com.midorimart.managementsystem.model.deliveryNote;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeliveryNoteDTOCreate {
    private String name;
    private Date createdAt;
    private int userId;
    private String note;
    private int orderId;
    private List<DeliveryDetailDTOCreate> deliveryDetail;
}
