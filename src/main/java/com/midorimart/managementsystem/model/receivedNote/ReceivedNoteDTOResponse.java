package com.midorimart.managementsystem.model.receivedNote;

import java.util.List;

import com.midorimart.managementsystem.model.merchant.dto.MerchantDTOResponse;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ReceivedNoteDTOResponse {
    private int id;
    private String name;
    private String createdAt;
    private String createdBy;
    private String note;
    private MerchantDTOResponse merchant;
    private int status;
    private List<ReceivedDetailDTOResponse> receivedDetail;
}
