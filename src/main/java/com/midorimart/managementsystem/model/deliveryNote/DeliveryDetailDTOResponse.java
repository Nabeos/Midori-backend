package com.midorimart.managementsystem.model.deliveryNote;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DeliveryDetailDTOResponse {
    private int id;
    private String title;
    private String sku;
    private int quantityExport;
    private double price;
    private double totalPrice;
}
