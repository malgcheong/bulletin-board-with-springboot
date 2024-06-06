package com.swjungle.board.post.service;


import com.swjungle.board.common.dto.EnvelopeResponse;
import com.swjungle.board.common.dto.MessageResponse;
import com.swjungle.board.post.dto.CreatePostRequest;
import com.swjungle.board.post.dto.PostResponse;
import com.swjungle.board.post.dto.PostWrapperListResponse;
import com.swjungle.board.post.dto.UpdatePostRequest;
import com.swjungle.board.post.entity.Post;
import com.swjungle.board.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public PostResponse createPost(CreatePostRequest createPostRequest) {
        Post post = Post.builder()
                .title(createPostRequest.title())
                .content(createPostRequest.content()).link(createPostRequest.link())
                .category(createPostRequest.category()).score(createPostRequest.score())
                .author(createPostRequest.author()).password(createPostRequest.password())
                .build();

        Post newPost = postRepository.save(post);
        return PostResponse.fromEntity(newPost);
    }
    public List<PostResponse> getAllPosts() {
        return  PostResponse.valueOf(postRepository.findAll());
    }

    public PostResponse getPostById(Long id) {
        return PostResponse.fromEntity(postRepository.findById(id).orElse(null));
    }

    public PostResponse updatePost(Long id, UpdatePostRequest updatePostRequest) {
        Post existingPost = postRepository.getReferenceById(id);
        existingPost.update(updatePostRequest.title(), updatePostRequest.content(), updatePostRequest.link(), updatePostRequest.category(), updatePostRequest.score(), updatePostRequest.author(), updatePostRequest.password());
        return PostResponse.fromEntity(postRepository.save(existingPost));
    }

    public MessageResponse deletePost(Long id) {
        Post existingPost = postRepository.getReferenceById(id);
        postRepository.deleteById(id);
        return new MessageResponse("삭제 완료");
    }
}
