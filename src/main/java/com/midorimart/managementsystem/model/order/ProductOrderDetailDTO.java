package com.midorimart.managementsystem.model.order;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductOrderDetailDTO {
    private String sku;
    private String productName;
}
