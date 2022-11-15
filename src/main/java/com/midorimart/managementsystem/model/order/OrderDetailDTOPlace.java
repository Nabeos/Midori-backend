package com.midorimart.managementsystem.model.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class OrderDetailDTOPlace {
    private int productId;
    private int quantity;
    private float price;
    private float totalPrice;
}
