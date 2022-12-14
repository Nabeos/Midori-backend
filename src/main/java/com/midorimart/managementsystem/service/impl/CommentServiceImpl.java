package com.midorimart.managementsystem.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.midorimart.managementsystem.entity.Comment;
import com.midorimart.managementsystem.entity.Product;
import com.midorimart.managementsystem.entity.User;
import com.midorimart.managementsystem.exception.custom.CustomBadRequestException;
import com.midorimart.managementsystem.model.CustomError;
import com.midorimart.managementsystem.model.comment.dto.CommentDTOCreate;
import com.midorimart.managementsystem.model.comment.dto.CommentDTOResponse;
import com.midorimart.managementsystem.model.comment.dto.CommentDTOUpdate;
import com.midorimart.managementsystem.model.mapper.CommentMapper;
import com.midorimart.managementsystem.repository.CommentRepository;
import com.midorimart.managementsystem.repository.ProductRepository;
import com.midorimart.managementsystem.service.CommentService;
import com.midorimart.managementsystem.service.UserService;
import com.midorimart.managementsystem.utils.NumberHelperUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final ProductRepository productRepository;
    private final UserService userService;

    // Add new comment
    @Override
    public Map<String, CommentDTOResponse> addComment(String slug, Map<String, CommentDTOCreate> commentDTOMap)
            throws CustomBadRequestException {
        CommentDTOCreate commentDTOCreate = commentDTOMap.get("comment");
        User user = userService.getUserLogin();
        Product product = productRepository.findBySlug(slug).get();
        if (checkBoughtProduct(product, user)) {
            Comment comment = CommentMapper.toComment(commentDTOCreate);
            comment.setUser(user);
            comment.setProduct(product);
            comment = commentRepository.save(comment);
            return buildCommentDTOResponse(comment);
        }
        throw new CustomBadRequestException(
                CustomError.builder().code("400").message("You must buy product first").build());
    }

    // Check if bought product or not
    private boolean checkBoughtProduct(Product product, User user) {
        boolean isBought = false;
        for (User u : product.getUsers()) {
            if (u.getId() == user.getId()) {
                isBought = true;
                return isBought;
            }
        }
        return isBought;
    }

    private Map<String, CommentDTOResponse> buildCommentDTOResponse(Comment comment) {
        Map<String, CommentDTOResponse> wrapper = new HashMap<>();
        CommentDTOResponse commentDTOResponse = CommentMapper.toCommentDTOResponse(comment);
        wrapper.put("comment", commentDTOResponse);
        return wrapper;
    }

    // Get star rate
    @Override
    public Map<String, Double> getAverageStarForEachProduct(int id) {
        Optional<Double> avgStarOptional = commentRepository.findAvgStarByProduct(id);
        Map<String, Double> wrapper = new HashMap<>();
        if (avgStarOptional.isPresent())
            wrapper.put("avgStar", NumberHelperUtil.fixNumberDecimal(avgStarOptional.get()));
        return wrapper;
    }

    // Update comment
    @Override
    public Map<String, CommentDTOResponse> updateComment(int id,
            Map<String, CommentDTOUpdate> commentDTOMap) throws CustomBadRequestException {
        CommentDTOUpdate commentDTOUpdate = commentDTOMap.get("comment");
        Comment comment = commentRepository.findById(id).get();
        User user = userService.getUserLogin();
        // Check if comment belongs to user or not
        if (user.getId() != comment.getUser().getId()) {
            throw new CustomBadRequestException(
                    CustomError.builder().code("400").message("You have no permission to edit this comment").build());
        }
        if (commentDTOUpdate.getContent() != null) {
            comment.setContent(commentDTOUpdate.getContent());
        }
        if (commentDTOUpdate.getStarRate() >= 0) {
            comment.setStarRate(commentDTOUpdate.getStarRate());
        }
        comment.setUpdatedAt(new Date());
        comment = commentRepository.save(comment);

        return buildCommentDTOResponse(comment);
    }

}
