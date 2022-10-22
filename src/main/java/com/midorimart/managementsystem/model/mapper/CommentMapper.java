package com.midorimart.managementsystem.model.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.midorimart.managementsystem.entity.Comment;
import com.midorimart.managementsystem.model.comment.dto.CommentDTOResponse;
import com.midorimart.managementsystem.model.comment.dto.CommentUserDTOResponse;

public class CommentMapper {

    public static List<CommentDTOResponse> toListCommentDTOResponse(List<Comment> comments) {
        List<CommentDTOResponse> commentDTOResponses = comments.stream().map(CommentMapper::toCommentDTOResponse)
                .collect(Collectors.toList());
        return commentDTOResponses;
    }

    public static CommentDTOResponse toCommentDTOResponse(Comment comment) {
        return CommentDTOResponse.builder().id(comment.getId()).content(comment.getContent())
                .createdAt(comment.getCreatedAt()).updatedAt(comment.getUpdatedAt())
                .user(CommentUserDTOResponse.builder().fullname(comment.getUser().getFullname()).build()).build();
    }
}
