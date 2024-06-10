package com.swjungle.board.common.advice;

import com.swjungle.board.comment.exception.CommentAuthorizationException;
import com.swjungle.board.comment.exception.CommentNotFoundException;
import com.swjungle.board.common.code.CommonErrorCode;
import com.swjungle.board.common.dto.EnvelopeResponse;
import com.swjungle.board.member.exception.DuplicateMemberException;
import com.swjungle.board.member.exception.InvalidMemberRequestException;
import com.swjungle.board.member.exception.InvalidPasswordException;
import com.swjungle.board.member.exception.MemberNotFoundException;
import com.swjungle.board.post.exception.InvalidPostRequestException;
import com.swjungle.board.post.exception.PostAuthorizationException;
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
    @ExceptionHandler(InvalidMemberRequestException.class)
    public ResponseEntity<EnvelopeResponse<List<String>>> handleInvalidMemberRequestException(InvalidMemberRequestException ex) {
        List<String> errorMessages = ex.getErrors();
        return ResponseEntity.status(CommonErrorCode.BAD_REQUEST.getHttpStatus())
                .body(EnvelopeResponse.error(CommonErrorCode.BAD_REQUEST, errorMessages));
    }
    @ExceptionHandler(DuplicateMemberException.class)
    public ResponseEntity<EnvelopeResponse<String>> handleDuplicateMemberException(DuplicateMemberException ex) {
        return ResponseEntity.status(CommonErrorCode.DUPLICATE_ACCOUNT.getHttpStatus())
                .body(EnvelopeResponse.error(CommonErrorCode.DUPLICATE_ACCOUNT, ex.getMessage()));
    }
    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<EnvelopeResponse<String>> handleMemberNotFoundException(MemberNotFoundException ex) {
        return ResponseEntity.status(CommonErrorCode.NO_ACCOUNT.getHttpStatus())
                .body(EnvelopeResponse.error(CommonErrorCode.NO_ACCOUNT, ex.getMessage()));
    }
    @ExceptionHandler(PostAuthorizationException.class)
    public ResponseEntity<EnvelopeResponse<String>> handlePostAuthorizationException(PostAuthorizationException ex) {
        return ResponseEntity.status(CommonErrorCode.NO_ACCOUNT.getHttpStatus())
                .body(EnvelopeResponse.error(CommonErrorCode.NO_ACCOUNT, ex.getMessage()));
    }
    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<EnvelopeResponse<String>> handleInvalidPasswordException(InvalidPasswordException ex) {
        return ResponseEntity.status(CommonErrorCode.WRONG_PASSWORD.getHttpStatus())
                .body(EnvelopeResponse.error(CommonErrorCode.WRONG_PASSWORD, ex.getMessage()));
    }
    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<EnvelopeResponse<String>> handleCommentNotFoundException(CommentNotFoundException ex) {
        return ResponseEntity.status(CommonErrorCode.COMMENT_NOT_FOUND.getHttpStatus())
                .body(EnvelopeResponse.error(CommonErrorCode.COMMENT_NOT_FOUND, ex.getMessage()));
    }
    @ExceptionHandler(CommentAuthorizationException.class)
    public ResponseEntity<EnvelopeResponse<String>> handleCommentAuthorizationException(CommentAuthorizationException ex) {
        return ResponseEntity.status(CommonErrorCode.COMMENT_UNAUTHORIZED.getHttpStatus())
                .body(EnvelopeResponse.error(CommonErrorCode.COMMENT_UNAUTHORIZED, ex.getMessage()));
    }
}