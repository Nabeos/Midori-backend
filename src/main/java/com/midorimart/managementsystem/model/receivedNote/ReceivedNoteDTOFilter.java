package com.midorimart.managementsystem.model.receivedNote;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ReceivedNoteDTOFilter {
    private int userId;
    private int merchantId;
    private int offset;
    private int limit;
    private String firstDate;
    private String secondDate;
}
