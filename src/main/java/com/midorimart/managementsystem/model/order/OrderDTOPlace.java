package com.midorimart.managementsystem.model.order;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class OrderDTOPlace {
    private String fullName;
    private String email;
    private String phoneNumber;
    private int receiveProductsMethod;
    private List<String> address;
    private List<OrderDetailDTOPlace> cart;
    private int paymentMethod;
    private String deliveryDate;
    private String deliveryTimeRange;
    private String note;
    private float totalBill;





}
