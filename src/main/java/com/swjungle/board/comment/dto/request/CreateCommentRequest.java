package com.swjungle.board.comment.dto.request;

public record CreateCommentRequest(Long postId, String content) {
}
