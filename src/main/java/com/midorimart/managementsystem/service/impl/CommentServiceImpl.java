package com.midorimart.managementsystem.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.midorimart.managementsystem.entity.Comment;
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
import com.midorimart.managementsystem.service.OrderService;
import com.midorimart.managementsystem.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final ProductRepository productRepository;
    private final UserService userService;

    @Override
    public Map<String, CommentDTOResponse> addComment(String slug, Map<String, CommentDTOCreate> commentDTOMap) throws CustomBadRequestException {
        CommentDTOCreate commentDTOCreate = commentDTOMap.get("comment");
        User user = userService.getUserLogin();
        if(checkBoughtProduct(user)){
            Comment comment = CommentMapper.toComment(commentDTOCreate);
            comment.setUser(user);
            comment.setProduct(productRepository.findBySlug(slug).get());
            comment = commentRepository.save(comment);
            return buildCommentDTOResponse(comment);
        }
        throw new CustomBadRequestException(CustomError.builder().code("400").message("You must buy product first").build());
    }

    private boolean checkBoughtProduct(User user) {
        return true;
    }

    private Map<String, CommentDTOResponse> buildCommentDTOResponse(Comment comment) {
        Map<String, CommentDTOResponse> wrapper = new HashMap<>();
        CommentDTOResponse commentDTOResponse = CommentMapper.toCommentDTOResponse(comment);
        wrapper.put("comment", commentDTOResponse);
        return wrapper;
    }

    @Override
    public Map<String, Double> getAverageStarForEachProduct(int id) {
        Optional<Double> avgStarOptional = commentRepository.findAvgStarByProduct(id);
        Map<String, Double> wrapper = new HashMap<>();
        if (avgStarOptional.isPresent())
            wrapper.put("avgStar",
                    BigDecimal.valueOf(avgStarOptional.get()).setScale(2, RoundingMode.HALF_UP).doubleValue());
        return wrapper;
    }

    @Override
    public Map<String, CommentDTOResponse> updateComment(int id,
            Map<String, CommentDTOUpdate> commentDTOMap) throws CustomBadRequestException {
        CommentDTOUpdate commentDTOUpdate = commentDTOMap.get("comment");
        Comment comment = commentRepository.findById(id).get();
        User user = userService.getUserLogin();
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
