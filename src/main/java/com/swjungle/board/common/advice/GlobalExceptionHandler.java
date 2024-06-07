package com.swjungle.board.common.advice;

import com.swjungle.board.common.code.CommonErrorCode;
import com.swjungle.board.common.dto.EnvelopeResponse;
import com.swjungle.board.post.exception.PostNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice // 모든 @RestController에 대한 예외 처리
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<EnvelopeResponse<String>> handleException(Exception ex) {
        return ResponseEntity.status(CommonErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(EnvelopeResponse.error(CommonErrorCode.INTERNAL_SERVER_ERROR));
    }

}