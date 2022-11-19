package com.midorimart.managementsystem.model.deliveryNote;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DeliveryNoteDTOResponse {
    private int id;
    private String name;
}
