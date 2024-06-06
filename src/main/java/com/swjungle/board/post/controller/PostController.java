package com.swjungle.board.post.controller;

import com.swjungle.board.common.dto.EnvelopeResponse;
import com.swjungle.board.post.dto.CreatePostRequest;
import com.swjungle.board.post.dto.PostWrapperListResponse;
import com.swjungle.board.post.dto.PostWrapperResponse;
import com.swjungle.board.post.dto.PostResponse;
import com.swjungle.board.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping
    public ResponseEntity<EnvelopeResponse<PostWrapperListResponse>> getAllPosts() {
        List<PostResponse> listPostResponse = PostResponse.valueOf(new ArrayList<>(postService.getAllTodos()));
        PostWrapperListResponse postData = new PostWrapperListResponse(listPostResponse);
        return ResponseEntity.ok().body(EnvelopeResponse.success(postData));
    }


}
