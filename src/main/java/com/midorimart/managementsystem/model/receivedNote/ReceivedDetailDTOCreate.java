package com.midorimart.managementsystem.model.receivedNote;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReceivedDetailDTOCreate {
    private int productId;
    private int quantityImport;
    private Date expiryDate;
    private double price;
    private double totalPrice;
}
