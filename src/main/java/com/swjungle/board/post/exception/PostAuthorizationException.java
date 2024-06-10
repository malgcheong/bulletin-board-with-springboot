package com.swjungle.board.post.exception;

public class PostAuthorizationException extends RuntimeException {
    public PostAuthorizationException(String username) {
        super(String.format("User '%s' is not authorized to delete post", username));
    }

}
