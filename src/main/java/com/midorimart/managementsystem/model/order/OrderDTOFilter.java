package com.midorimart.managementsystem.model.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTOFilter {
    private int offset;
    private int limit;
    private int status;
}
