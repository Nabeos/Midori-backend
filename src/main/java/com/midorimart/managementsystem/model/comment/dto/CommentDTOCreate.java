package com.midorimart.managementsystem.model.comment.dto;

import com.midorimart.managementsystem.model.users.UserDTOResponse;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CommentDTOCreate {
    private String content;
    private double starRate;
}
