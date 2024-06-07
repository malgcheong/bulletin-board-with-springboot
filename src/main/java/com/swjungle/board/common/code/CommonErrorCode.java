package com.swjungle.board.common.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode {

    INVALID_PARAMETER(Status.FAIL.name(), HttpStatus.BAD_REQUEST, "Invalid parameter included"),
    RESOURCE_NOT_FOUND(Status.FAIL.name(), HttpStatus.NOT_FOUND, "Resource not exists"),
    POST_NOT_FOUND(Status.FAIL.name(), HttpStatus.INTERNAL_SERVER_ERROR, "Post not found with id"),
    INTERNAL_SERVER_ERROR(Status.ERROR.name(), HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");

    private final String status;
    private final HttpStatus httpStatus;
    private final String message;

    private enum Status {
        SUCCESS,
        FAIL,
        ERROR
    }
}