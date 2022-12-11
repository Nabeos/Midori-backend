package com.midorimart.managementsystem.model.address.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTOResponse {
    private String provinceId;
    private String districtId;
    private String wardId;
    @NotBlank
    private String addressDetail;
}
