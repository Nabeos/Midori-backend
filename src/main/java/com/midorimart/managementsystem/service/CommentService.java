package com.midorimart.managementsystem.service;

import java.util.Map;

import com.midorimart.managementsystem.model.comment.dto.CommentDTOCreate;
import com.midorimart.managementsystem.model.comment.dto.CommentDTOResponse;

public interface CommentService {

    Map<String, CommentDTOResponse> addComment(String slug, Map<String, CommentDTOCreate> commentDTOMap);

}
