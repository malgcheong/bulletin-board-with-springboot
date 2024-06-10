package com.swjungle.board.comment.exception;

public class CommentAuthorizationException extends RuntimeException {
    public CommentAuthorizationException(String username, String action) {
        super(String.format("User '%s' is not authorized to %s comment", username, action));
    }
}
