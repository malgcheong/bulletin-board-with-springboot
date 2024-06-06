package com.swjungle.board.post.controller;

import com.swjungle.board.common.dto.EnvelopeResponse;
import com.swjungle.board.post.dto.CreatePostRequest;
import com.swjungle.board.post.dto.PostWrapperResponse;
import com.swjungle.board.post.dto.PostResponse;
import com.swjungle.board.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<EnvelopeResponse<PostWrapperResponse>> createPost(@RequestBody CreatePostRequest createPostRequest) {
        PostResponse postResponse = postService.createPost(createPostRequest);
        PostWrapperResponse postData = new PostWrapperResponse(postResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(EnvelopeResponse.success(postData));
    }

}
