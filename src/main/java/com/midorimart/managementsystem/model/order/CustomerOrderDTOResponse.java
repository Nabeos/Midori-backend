package com.midorimart.managementsystem.model.order;

import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerOrderDTOResponse {
    private String orderNumber;
    private String fullName;
    private String phoneNumber;
    private String address;
    private String notes;
    private String createdDate;
    private String receiveDateTime;
    private String orderStatus;
    private String receiveProductsMethod;
    private Map<String, List<OrderDetailDTOResponse>> cart;
}
