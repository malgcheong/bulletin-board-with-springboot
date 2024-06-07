package com.swjungle.board.post.exception;

import org.springframework.validation.Errors;

import java.util.List;

public class InvalidPostRequestException extends RuntimeException {
    private List<String> errors;

    public InvalidPostRequestException(List<String> errors) {
        super("Invalid post request");
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
