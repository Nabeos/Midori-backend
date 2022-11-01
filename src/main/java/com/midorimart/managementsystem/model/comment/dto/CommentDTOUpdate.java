package com.midorimart.managementsystem.model.comment.dto;

import com.midorimart.managementsystem.model.users.UserDTOResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTOUpdate {
    private String content;
    private double starRate;
}
