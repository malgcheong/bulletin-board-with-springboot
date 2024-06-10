package com.swjungle.board.common.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode {

    WRONG_PASSWORD(Status.FAIL.name(), HttpStatus.BAD_REQUEST, "Password is invalid"),
    NO_ACCOUNT(Status.FAIL.name(), HttpStatus.NOT_FOUND, "Member not found with username"),
    POST_UNAUTHORIZED(Status.FAIL.name(), HttpStatus.UNAUTHORIZED, "User is not authorized to access this resource"),
    DUPLICATE_ACCOUNT(Status.FAIL.name(), HttpStatus.CONFLICT, "Username already in use"),
    BAD_REQUEST(Status.FAIL.name(), HttpStatus.BAD_REQUEST, "Invalid parameter included"),
    NOT_FOUND(Status.FAIL.name(), HttpStatus.NOT_FOUND, "Resource not exists"),
    POST_NOT_FOUND(Status.FAIL.name(), HttpStatus.NOT_FOUND, "Post not found with id"),
    SERVER_ERROR(Status.ERROR.name(), HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");

    private final String status;
    private final HttpStatus httpStatus;
    private final String message;

    private enum Status {
        SUCCESS,
        FAIL,
        ERROR
    }
}