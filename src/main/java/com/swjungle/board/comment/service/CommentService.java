package com.swjungle.board.comment.service;

import com.swjungle.board.comment.dto.request.CreateCommentRequest;
import com.swjungle.board.comment.dto.request.UpdateCommentRequest;
import com.swjungle.board.comment.dto.response.CommentResponse;
import com.swjungle.board.comment.entity.Comment;
import com.swjungle.board.comment.exception.CommentAuthorizationException;
import com.swjungle.board.comment.exception.CommentNotFoundException;
import com.swjungle.board.comment.repository.CommentRepository;
import com.swjungle.board.common.dto.MessageResponse;
import com.swjungle.board.member.entity.Member;
import com.swjungle.board.member.exception.MemberNotFoundException;
import com.swjungle.board.member.repository.MemberRepository;
import com.swjungle.board.post.entity.Post;
import com.swjungle.board.post.exception.PostNotFoundException;
import com.swjungle.board.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    public CommentResponse createComment(CreateCommentRequest createCommentRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new MemberNotFoundException()); // 회원 조회 실패 시 예외 처리

        Post existingPost = postRepository.findById(createCommentRequest.postId()).orElseThrow(() -> new PostNotFoundException(createCommentRequest.postId()));

        Comment comment = Comment.of(
                member,
                existingPost,
                createCommentRequest.content()
        );

        Comment newComment = commentRepository.save(comment);
        return CommentResponse.fromEntity(newComment);
    }

    public List<CommentResponse> getAllComments() {
        return  CommentResponse.valueOf(commentRepository.findAllByOrderByUpdatedAtDesc());
    }

    public CommentResponse getCommentById(Long id) {
        return CommentResponse.fromEntity(commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException(id)));
    }

    public CommentResponse updateComment(Long id, UpdateCommentRequest updateCommentRequest) {
        Comment existingComment = commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException(id));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername().toString();

        if (!existingComment.getMember().getUsername().equals(username)) // username 검증
            throw new CommentAuthorizationException(username, "update"); // 권한 예외 발생

        existingComment.update(
                updateCommentRequest.content()
        );

        return CommentResponse.fromEntity(commentRepository.save(existingComment));
    }

    public MessageResponse deleteComment(Long id) {
        Comment existingComment = commentRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername().toString();

        if (!existingComment.getMember().getUsername().equals(username)) // username 검증
            throw new CommentAuthorizationException(username, "delete"); // 권한 예외 발생

        commentRepository.deleteById(id);
        return new MessageResponse("삭제 완료");
    }

}

