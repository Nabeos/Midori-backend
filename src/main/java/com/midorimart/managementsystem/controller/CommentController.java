package com.midorimart.managementsystem.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.midorimart.managementsystem.exception.custom.CustomBadRequestException;
import com.midorimart.managementsystem.model.comment.dto.CommentDTOCreate;
import com.midorimart.managementsystem.model.comment.dto.CommentDTOResponse;
import com.midorimart.managementsystem.model.comment.dto.CommentDTOUpdate;
import com.midorimart.managementsystem.service.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Comment API")
public class CommentController {
    private final CommentService commentService;

    @Operation(summary = "Get Average Star for each Product")
    @GetMapping("/products/{product-id}/stars")
    public Map<String, Double> getAverageStarForEachProduct(@PathVariable(name = "product-id") int id) {
        return commentService.getAverageStarForEachProduct(id);
    }

    @Operation(summary = "Add comment API")
    @PostMapping("/v1/products/{slug}/comments")
    public Map<String, CommentDTOResponse> addComment(@PathVariable String slug,
            @RequestBody Map<String, CommentDTOCreate> commentDTOMap) throws CustomBadRequestException {
        return commentService.addComment(slug, commentDTOMap);
    }

    @Operation(summary = "Update comment API")
    @PutMapping("/v1/products/comments/{id}")
    public Map<String, CommentDTOResponse> updateComment(@PathVariable int id,
            @RequestBody Map<String, CommentDTOUpdate> commentDTOMap) throws CustomBadRequestException {
        return commentService.updateComment(id, commentDTOMap);
    }
}
