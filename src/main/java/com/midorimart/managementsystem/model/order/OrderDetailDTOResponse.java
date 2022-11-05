package com.midorimart.managementsystem.model.order;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDetailDTOResponse {
    private String sku;
    private String thumbnails;
    private String productName;
    private double price;
    private int quantity;
    private double totalPrice;
}
