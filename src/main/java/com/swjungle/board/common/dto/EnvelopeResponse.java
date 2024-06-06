package com.swjungle.board.common.dto;

public record EnvelopeResponse<T> (int code, String message, T data) {

    // 성공 응답 생성
    public static <T> EnvelopeResponse<T> success(T data) {
        return new EnvelopeResponse<>(0, null, data);
    }

    // 에러 응답 생성
    public static <T> EnvelopeResponse<T> error(int code, String message, T data) {
        return new EnvelopeResponse<>(code, message, data);
    }
}
