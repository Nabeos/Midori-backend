package com.midorimart.managementsystem.service;

import java.util.Map;

import com.midorimart.managementsystem.exception.custom.CustomBadRequestException;
import com.midorimart.managementsystem.model.comment.dto.CommentDTOCreate;
import com.midorimart.managementsystem.model.comment.dto.CommentDTOResponse;
import com.midorimart.managementsystem.model.comment.dto.CommentDTOUpdate;

public interface CommentService {

    Map<String, CommentDTOResponse> addComment(String slug, Map<String, CommentDTOCreate> commentDTOMap) throws CustomBadRequestException;

    Map<String, Double> getAverageStarForEachProduct(int id);

    Map<String, CommentDTOResponse> updateComment(int id, Map<String, CommentDTOUpdate> commentDTOMap) throws CustomBadRequestException;

}
