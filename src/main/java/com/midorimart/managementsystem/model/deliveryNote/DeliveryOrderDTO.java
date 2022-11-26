package com.midorimart.managementsystem.model.deliveryNote;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeliveryOrderDTO {
    private int id;
    private String orderDate;
    private String orderNumber;
    private String receiveProductsMethod;
    private float totalBill;
    private String notes;
    private String status;
}
