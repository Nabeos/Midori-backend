package com.midorimart.managementsystem.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.midorimart.managementsystem.entity.Comment;
import com.midorimart.managementsystem.entity.User;
import com.midorimart.managementsystem.model.comment.dto.CommentDTOCreate;
import com.midorimart.managementsystem.model.comment.dto.CommentDTOResponse;
import com.midorimart.managementsystem.model.mapper.CommentMapper;
import com.midorimart.managementsystem.repository.CommentRepository;
import com.midorimart.managementsystem.repository.ProductRepository;
import com.midorimart.managementsystem.service.CommentService;
import com.midorimart.managementsystem.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final ProductRepository productRepository;
    private final UserService userService;

    @Override
    public Map<String, CommentDTOResponse> addComment(String slug, Map<String, CommentDTOCreate> commentDTOMap) {
        CommentDTOCreate commentDTOCreate = commentDTOMap.get("comment");
        Comment comment = CommentMapper.toComment(commentDTOCreate);
        User user = userService.getUserLogin();
        comment.setUser(user);
        comment.setProduct(productRepository.findBySlug(slug).get());
        comment = commentRepository.save(comment);
        return buildCommentDTOResponse(comment);
    }

    private Map<String, CommentDTOResponse> buildCommentDTOResponse(Comment comment) {
        Map<String, CommentDTOResponse> wrapper = new HashMap<>();
        CommentDTOResponse commentDTOResponse = CommentMapper.toCommentDTOResponse(comment);
        wrapper.put("comment", commentDTOResponse);
        return wrapper;
    }

}
