package com.midorimart.managementsystem.model.deliveryNote;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DeliveryDetailDTOCreate {
    private int productId;
    private int quantityExport;
    private Date expiryDate;
    private double price;
    private double totalPrice;
}
