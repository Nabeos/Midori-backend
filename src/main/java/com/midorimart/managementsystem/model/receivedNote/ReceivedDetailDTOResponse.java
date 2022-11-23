package com.midorimart.managementsystem.model.receivedNote;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ReceivedDetailDTOResponse {
    private int id;
    private String title;
    private String sku;
    private int quantityImport;
    private String expiryDate;
    private double price;
    private double totalPrice;
}
