package com.midorimart.managementsystem.model.address.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WardDTOResponse {
    private String id;
    private String name;
}
