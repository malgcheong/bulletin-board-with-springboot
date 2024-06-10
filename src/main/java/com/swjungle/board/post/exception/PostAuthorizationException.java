package com.swjungle.board.post.exception;

public class PostAuthorizationException extends RuntimeException {
    public PostAuthorizationException(String username, String action) {
        super(String.format("User '%s' is not authorized to %s post", username, action));
    }

}
