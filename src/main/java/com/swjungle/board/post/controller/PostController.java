package com.swjungle.board.post.controller;

import com.swjungle.board.common.dto.EnvelopeResponse;
import com.swjungle.board.common.dto.MessageResponse;
import com.swjungle.board.post.dto.request.CreatePostRequest;
import com.swjungle.board.post.dto.request.DeletePostRequest;
import com.swjungle.board.post.dto.request.UpdatePostRequest;
import com.swjungle.board.post.dto.response.PostResponse;
import com.swjungle.board.post.dto.response.PostWrapperListResponse;
import com.swjungle.board.post.dto.response.PostWrapperResponse;
import com.swjungle.board.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        List<PostResponse> listPostResponse = postService.getAllPosts();
        PostWrapperListResponse postData = new PostWrapperListResponse(listPostResponse);
        return ResponseEntity.ok().body(EnvelopeResponse.success(postData));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnvelopeResponse<PostWrapperResponse>> getPostById(@PathVariable Long id) {
        PostResponse postResponse = postService.getPostById(id);
        PostWrapperResponse postData = new PostWrapperResponse(postResponse);
        return ResponseEntity.ok().body(EnvelopeResponse.success(postData));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnvelopeResponse<PostWrapperResponse>> updatePost(@PathVariable Long id, @RequestBody UpdatePostRequest updatePostRequest) {
        PostResponse postResponse = postService.updatePost(id, updatePostRequest);
        PostWrapperResponse postData = new PostWrapperResponse(postResponse);
        return ResponseEntity.ok().body(EnvelopeResponse.success(postData));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EnvelopeResponse<MessageResponse>> deletePost(@PathVariable Long id, @RequestBody DeletePostRequest deletePostRequest) {
        MessageResponse messageResponse = postService.deletePost(id, deletePostRequest);
        return ResponseEntity.ok().body(EnvelopeResponse.success(messageResponse));
    }
}
