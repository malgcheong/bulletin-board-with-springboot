package com.swjungle.board.post.service;


import com.swjungle.board.common.dto.MessageResponse;
import com.swjungle.board.post.dto.request.CreatePostRequest;
import com.swjungle.board.post.dto.response.PostResponse;
import com.swjungle.board.post.dto.request.UpdatePostRequest;
import com.swjungle.board.post.entity.Post;
import com.swjungle.board.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public PostResponse createPost(CreatePostRequest createPostRequest) {
        Post post = Post.of(createPostRequest.title(), createPostRequest.content(), createPostRequest.link(), createPostRequest.category(), createPostRequest.score(), createPostRequest.author(),createPostRequest.password());
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
