package com.midorimart.managementsystem.model.receivedNote;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReceivedNoteDTOCreate {
    private String name;
    private Date createdAt;
    private int userId;
    private String note;
    private int merchant;
    private List<ReceivedDetailDTOCreate> receivedDetail;
}
