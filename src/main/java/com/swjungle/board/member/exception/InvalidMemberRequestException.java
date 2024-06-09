package com.swjungle.board.member.exception;

import java.util.List;

public class InvalidMemberRequestException extends RuntimeException {
    private List<String> errors;

    public InvalidMemberRequestException(List<String> errors) {
        super("Invalid member request");
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
