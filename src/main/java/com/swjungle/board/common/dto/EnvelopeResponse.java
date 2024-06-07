package com.swjungle.board.common.dto;

import com.swjungle.board.common.code.CommonErrorCode;

import java.util.List;

public record EnvelopeResponse<T> (String code, String message, T data) {

    // 성공 응답 생성
    public static <T> EnvelopeResponse<T> success(T data) {
        return new EnvelopeResponse<>(null, null, data);
    }

    // 에러 응답 생성
    public static <T> EnvelopeResponse<T> error(CommonErrorCode commonErrorCode, String message) {
        if (message != null)
            return new EnvelopeResponse<>(commonErrorCode.getStatus(), message, null);
        else
            return new EnvelopeResponse<>(commonErrorCode.getStatus(), commonErrorCode.getMessage(), null);
    }

    // 에러 응답 생성 (List<String> messages)
    public static EnvelopeResponse<List<String>> error(CommonErrorCode errorCode, List<String> messages) {
        String errorMessage = String.join(", ", messages);
        return new EnvelopeResponse<>(errorCode.getStatus(), errorMessage, null);
    }

}
