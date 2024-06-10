package com.swjungle.board.comment.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.swjungle.board.comment.entity.Comment;
import com.swjungle.board.post.dto.response.PostResponse;
import com.swjungle.board.post.entity.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record CommentResponse(
        @JsonProperty("id") Long id,
        @JsonProperty("memberId") Long memberId,
        @JsonProperty("postId") Long postId,
        @JsonProperty("content") String content,
        @JsonProperty("created_at") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime createdAt,
        @JsonProperty("updated_at") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime updatedAt
) {
    public static CommentResponse fromEntity(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getMember().getId(),
                comment.getPost().getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }

    public static List<CommentResponse> valueOf(List<Comment> commentList){
        /* 정적 팩토리 메서드 */
        return commentList.stream()
                .map(CommentResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
