package com.swjungle.board.common.advice;

import com.swjungle.board.common.code.CommonErrorCode;
import com.swjungle.board.common.dto.EnvelopeResponse;
import com.swjungle.board.member.exception.MemberNotFoundException;
import com.swjungle.board.post.exception.InvalidPostRequestException;
import com.swjungle.board.post.exception.PostNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestControllerAdvice // 모든 @RestController에 대한 예외 처리
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<EnvelopeResponse<String>> handleException(Exception ex) {
        return ResponseEntity.status(CommonErrorCode.SERVER_ERROR.getHttpStatus())
                .body(EnvelopeResponse.error(CommonErrorCode.SERVER_ERROR, ex.getMessage()));
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<EnvelopeResponse<String>> handleTodoNotFoundException(PostNotFoundException ex) {
        return ResponseEntity.status(CommonErrorCode.POST_NOT_FOUND.getHttpStatus())
                .body(EnvelopeResponse.error(CommonErrorCode.POST_NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(InvalidPostRequestException.class)
    public ResponseEntity<EnvelopeResponse<List<String>>> handleInvalidPostRequestException(InvalidPostRequestException ex) {
        List<String> errorMessages = ex.getErrors();
        return ResponseEntity.status(CommonErrorCode.BAD_REQUEST.getHttpStatus())
                .body(EnvelopeResponse.error(CommonErrorCode.BAD_REQUEST, errorMessages));
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<EnvelopeResponse<String>> handleMemberNotFoundException(MemberNotFoundException ex) {
        return ResponseEntity.status(CommonErrorCode.NO_ACCOUNT.getHttpStatus())
                .body(EnvelopeResponse.error(CommonErrorCode.NO_ACCOUNT, ex.getMessage()));
    }
}