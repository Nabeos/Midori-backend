package com.midorimart.managementsystem.model.comment.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CommentUserDTOResponse {
    private String fullname;
    private String thumbnail;
}
