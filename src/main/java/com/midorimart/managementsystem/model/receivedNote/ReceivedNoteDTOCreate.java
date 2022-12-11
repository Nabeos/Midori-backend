package com.midorimart.managementsystem.model.receivedNote;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
    @NotEmpty(message = "Thiếu tên")
    private String name;
    private Date createdAt;
    @NotNull(message = "Thiếu người tạo")
    private int userId;
    private String note;
    @NotNull(message = "Thiếu nhà cung cấp")
    private int merchant;
    private List<ReceivedDetailDTOCreate> receivedDetail;
}
