package com.midorimart.managementsystem.model.mapper;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.midorimart.managementsystem.entity.Comment;
import com.midorimart.managementsystem.model.comment.dto.CommentDTOCreate;
import com.midorimart.managementsystem.model.comment.dto.CommentDTOResponse;
import com.midorimart.managementsystem.model.comment.dto.CommentUserDTOResponse;

public class CommentMapper {

    public static List<CommentDTOResponse> toListCommentDTOResponse(List<Comment> comments) {
        List<CommentDTOResponse> commentDTOResponses = comments.stream().map(CommentMapper::toCommentDTOResponse)
                .collect(Collectors.toList());
        Collections.reverse(commentDTOResponses);
        return commentDTOResponses;
    }

    public static CommentDTOResponse toCommentDTOResponse(Comment comment) {
        return CommentDTOResponse.builder().id(comment.getId()).content(comment.getContent())
                .createdAt(comment.getCreatedAt()).updatedAt(comment.getUpdatedAt()).starRate(comment.getStarRate())
                .user(CommentUserDTOResponse.builder().fullname(comment.getUser().getFullname())
                        .thumbnail(comment.getUser().getThumbnail()).build())
                .build();
    }

    public static Comment toComment(CommentDTOCreate commentDTOCreate) {
        Date now = new Date();
        return Comment.builder().starRate(commentDTOCreate.getStarRate()).content(commentDTOCreate.getContent())
                .createdAt(now).updatedAt(now).build();
    }
}
