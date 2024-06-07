package com.swjungle.board.post.service;


import com.swjungle.board.common.dto.MessageResponse;
import com.swjungle.board.post.dto.request.CreatePostRequest;
import com.swjungle.board.post.dto.response.PostResponse;
import com.swjungle.board.post.dto.request.UpdatePostRequest;
import com.swjungle.board.post.entity.Post;
import com.swjungle.board.post.exception.InvalidPostRequestException;
import com.swjungle.board.post.exception.PostNotFoundException;
import com.swjungle.board.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostRequestValidator postRequestValidator;

    public PostResponse createPost(CreatePostRequest createPostRequest) {
        postRequestValidator.validate(createPostRequest); // 유효성 검증

        Post post = Post.of(
                createPostRequest.title(),
                createPostRequest.content(),
                createPostRequest.link(),
                createPostRequest.category(),
                createPostRequest.score(),
                createPostRequest.author(),
                createPostRequest.password()
        );
        Post newPost = postRepository.save(post);
        return PostResponse.fromEntity(newPost);
    }

    public List<PostResponse> getAllPosts() {
        return  PostResponse.valueOf(postRepository.findAll());
    }

    public PostResponse getPostById(Long id) {
        return PostResponse.fromEntity(postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id)));
    }

    public PostResponse updatePost(Long id, UpdatePostRequest updatePostRequest) {
        Post existingPost = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));

        postRequestValidator.validate(updatePostRequest); // 유효성 검증

        existingPost.update(
                updatePostRequest.title(),
                updatePostRequest.content(),
                updatePostRequest.link(),
                updatePostRequest.category(),
                updatePostRequest.score(),
                updatePostRequest.author(),
                updatePostRequest.password()
        );

        return PostResponse.fromEntity(postRepository.save(existingPost));
    }

    public MessageResponse deletePost(Long id) {
        Post existingPost = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
        postRepository.deleteById(id);
        return new MessageResponse("삭제 완료");
    }
}
