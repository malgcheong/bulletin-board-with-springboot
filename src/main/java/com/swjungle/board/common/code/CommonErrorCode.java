package com.swjungle.board.common.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode {

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