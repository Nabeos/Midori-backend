package com.midorimart.managementsystem.model.order;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class OrderDTOResponse {
    private int id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String receiveProductsMethod;
    private String address;
    private String notes;
    private float totalBill;
    private String orderNumber;
    private String status;
}
