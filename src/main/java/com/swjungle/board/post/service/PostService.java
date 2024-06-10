package com.swjungle.board.post.service;


import com.swjungle.board.common.dto.MessageResponse;
import com.swjungle.board.member.entity.Member;
import com.swjungle.board.member.exception.MemberNotFoundException;
import com.swjungle.board.member.repository.MemberRepository;
import com.swjungle.board.post.dto.request.CreatePostRequest;
import com.swjungle.board.post.dto.response.PostResponse;
import com.swjungle.board.post.dto.request.UpdatePostRequest;
import com.swjungle.board.post.entity.Post;
import com.swjungle.board.post.exception.InvalidPostRequestException;
import com.swjungle.board.post.exception.PostAuthorizationException;
import com.swjungle.board.post.exception.PostNotFoundException;
import com.swjungle.board.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostRequestValidator postRequestValidator;
    private final MemberRepository memberRepository;

    public PostResponse createPost(CreatePostRequest createPostRequest) {
        postRequestValidator.validate(createPostRequest); // 유효성 검증

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new MemberNotFoundException()); // 회원 조회 실패 시 예외 처리

        Post post = Post.of(
                member,
                createPostRequest.title(),
                createPostRequest.content(),
                createPostRequest.link(),
                createPostRequest.category(),
                createPostRequest.score(),
                username
        );
        Post newPost = postRepository.save(post);
        return PostResponse.fromEntity(newPost);
    }

    public List<PostResponse> getAllPosts() {
        return  PostResponse.valueOf(postRepository.findAllByOrderByUpdatedAtDesc());
    }

    public PostResponse getPostById(Long id) {
        return PostResponse.fromEntity(postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id)));
    }

    public PostResponse updatePost(Long id, UpdatePostRequest updatePostRequest) {
        Post existingPost = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));

        postRequestValidator.validate(updatePostRequest); // 유효성 검증

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername().toString();

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("USER")))
            if (!existingPost.getAuthor().equals(username)) // username 검증
                throw new PostAuthorizationException(username, "update"); // 권한 예외 발생

        existingPost.update(
                updatePostRequest.title(),
                updatePostRequest.content(),
                updatePostRequest.link(),
                updatePostRequest.category(),
                updatePostRequest.score()
        );

        return PostResponse.fromEntity(postRepository.save(existingPost));
    }

    public MessageResponse deletePost(Long id) {
        Post existingPost = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername().toString();

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("USER")))
            if (!existingPost.getAuthor().equals(username)) // username 검증
                throw new PostAuthorizationException(username, "delete"); // 권한 예외 발생

        postRepository.deleteById(id);
        return new MessageResponse("삭제 완료");
    }
}
