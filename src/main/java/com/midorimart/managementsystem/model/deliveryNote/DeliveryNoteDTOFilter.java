package com.midorimart.managementsystem.model.deliveryNote;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DeliveryNoteDTOFilter {
    private int userId;
    private int orderId;
    private int offset;
    private int limit;
    private String firstDate;
    private String secondDate;
}
