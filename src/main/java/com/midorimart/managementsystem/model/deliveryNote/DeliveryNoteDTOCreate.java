package com.midorimart.managementsystem.model.deliveryNote;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeliveryNoteDTOCreate {
    private String name;
}
