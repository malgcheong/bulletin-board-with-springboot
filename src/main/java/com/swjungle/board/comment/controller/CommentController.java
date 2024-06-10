package com.swjungle.board.comment.controller;

import com.swjungle.board.comment.dto.request.CreateCommentRequest;
import com.swjungle.board.comment.dto.request.UpdateCommentRequest;
import com.swjungle.board.comment.dto.response.CommentResponse;
import com.swjungle.board.comment.dto.response.CommentWrapperListResponse;
import com.swjungle.board.comment.dto.response.CommentWrapperResponse;
import com.swjungle.board.comment.service.CommentService;
import com.swjungle.board.common.dto.EnvelopeResponse;
import com.swjungle.board.common.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<EnvelopeResponse<CommentWrapperResponse>> createPost(@RequestBody CreateCommentRequest createCommentRequest) {
        CommentResponse commentResponse = commentService.createComment(createCommentRequest);
        CommentWrapperResponse commentData = new CommentWrapperResponse(commentResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(EnvelopeResponse.success(commentData));
    }

    @GetMapping
    public ResponseEntity<EnvelopeResponse<CommentWrapperListResponse>> getAllPosts() {
        List<CommentResponse> listCommentResponse = commentService.getAllComments();
        CommentWrapperListResponse commentData = new CommentWrapperListResponse(listCommentResponse);
        return ResponseEntity.ok().body(EnvelopeResponse.success(commentData));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnvelopeResponse<CommentWrapperResponse>> getPostById(@PathVariable Long id) {
        CommentResponse commentResponse = commentService.getCommentById(id);
        CommentWrapperResponse commentData = new CommentWrapperResponse(commentResponse);
        return ResponseEntity.ok().body(EnvelopeResponse.success(commentData));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnvelopeResponse<CommentWrapperResponse>> updatePost(@PathVariable Long id, @RequestBody UpdateCommentRequest updateCommentRequest) {
        CommentResponse commentResponse = commentService.updateComment(id, updateCommentRequest);
        CommentWrapperResponse commentData = new CommentWrapperResponse(commentResponse);
        return ResponseEntity.ok().body(EnvelopeResponse.success(commentData));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EnvelopeResponse<MessageResponse>> deletePost(@PathVariable Long id) {
        MessageResponse messageResponse = commentService.deleteComment(id);
        return ResponseEntity.ok().body(EnvelopeResponse.success(messageResponse));
    }
}

