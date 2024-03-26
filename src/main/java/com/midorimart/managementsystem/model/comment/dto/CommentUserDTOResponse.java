package com.midorimart.managementsystem.model.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentUserDTOResponse {
    private String fullname;
    private String thumbnail;
}
