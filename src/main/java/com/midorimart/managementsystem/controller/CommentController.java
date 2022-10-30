package com.midorimart.managementsystem.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.midorimart.managementsystem.model.comment.dto.CommentDTOCreate;
import com.midorimart.managementsystem.model.comment.dto.CommentDTOResponse;
import com.midorimart.managementsystem.service.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Comment API")
public class CommentController {
    private final CommentService commentService;

    @Operation(summary = "Add comment API")
    @PostMapping("/v1/products/{slug}/comments")
    public Map<String, CommentDTOResponse> addComment(@PathVariable String slug,
            @RequestBody Map<String, CommentDTOCreate> commentDTOMap) {
        return commentService.addComment(slug, commentDTOMap);
    }
}
