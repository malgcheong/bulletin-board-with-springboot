package com.swjungle.board.common.dto;

public record EnvelopeResponse<T> (int error, String message, T data) {

    // 성공 응답 생성
    public static <T> EnvelopeResponse<T> success(T data) {
        return new EnvelopeResponse<>(0, null, data);
    }

    // 에러 응답 생성
    public static <T> EnvelopeResponse<T> error(int error, String message, T data) {
        return new EnvelopeResponse<>(error, message, data);
    }
}
