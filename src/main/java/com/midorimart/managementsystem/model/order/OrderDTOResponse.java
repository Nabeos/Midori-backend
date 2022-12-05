package com.midorimart.managementsystem.model.order;

import java.util.List;

import com.midorimart.managementsystem.model.address.dto.AddressDTOResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTOResponse {
    private int id;
    private String orderDate;
    private String deliveryDate;
    private String deliveryTimeRange;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String receiveProductsMethod;
    private AddressDTOResponse address;
    private String notes;
    private float totalBill;
    private String orderNumber;
    private String status;
    private List<OrderDetailDTOResponse> orderDetail;
    private String orderCode;
}
