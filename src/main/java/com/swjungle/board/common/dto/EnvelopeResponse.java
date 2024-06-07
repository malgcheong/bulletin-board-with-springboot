package com.swjungle.board.common.dto;

import com.swjungle.board.common.code.CommonErrorCode;

public record EnvelopeResponse<T> (String code, String message, T data) {

    // 성공 응답 생성
    public static <T> EnvelopeResponse<T> success(T data) {
        return new EnvelopeResponse<>(null, null, data);
    }

    // 에러 응답 생성
    public static <T> EnvelopeResponse<T> error(CommonErrorCode commonErrorCode) {
        return new EnvelopeResponse<>(commonErrorCode.getStatus(), commonErrorCode.getMessage(), null);
    }
}
