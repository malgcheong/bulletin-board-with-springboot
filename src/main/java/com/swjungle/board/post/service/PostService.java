package com.swjungle.board.post.service;


import com.swjungle.board.post.dto.CreatePostRequest;
import com.swjungle.board.post.dto.PostResponse;
import com.swjungle.board.post.entity.Post;
import com.swjungle.board.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        return new PostResponse(newPost.getId(), newPost.getTitle(),
                                newPost.getContent(), newPost.getLink(),
                                newPost.getCategory(), newPost.getScore(),
                                newPost.getAuthor(), newPost.getCreatedAt(),
                                newPost.getUpdatedAt());
    }
}
