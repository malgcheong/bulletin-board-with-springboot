package com.swjungle.board.member.service;

import com.swjungle.board.member.dto.request.LoginRequest;
import com.swjungle.board.member.dto.request.SignupRequest;
import com.swjungle.board.member.exception.InvalidMemberRequestException; // 예외 클래스 변경
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class MemberRequestValidator {

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-z0-9]{4,10}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9]{8,15}$");

    public void validate(SignupRequest request) {
        List<String> errors = new ArrayList<>();

        // username 검증
        if (isBlank(request.username())) {
            errors.add("아이디는 필수 입력 값입니다.");
        } else if (!USERNAME_PATTERN.matcher(request.username()).matches()) {
            errors.add("아이디는 4~10자의 영문 소문자와 숫자만 사용 가능합니다.");
        }

        // password 검증
        if (isBlank(request.password())) {
            errors.add("비밀번호는 필수 입력 값입니다.");
        } else if (!PASSWORD_PATTERN.matcher(request.password()).matches()) {
            errors.add("비밀번호는 8~15자의 영문 대소문자와 숫자만 사용 가능합니다.");
        }

        if (!errors.isEmpty()) {
            throw new InvalidMemberRequestException(errors); // 예외 클래스 변경
        }
    }

    public void validate(LoginRequest request) {
        List<String> errors = new ArrayList<>();

        // username 검증
        if (isBlank(request.username())) {
            errors.add("아이디는 필수 입력 값입니다.");
        }

        // password 검증
        if (isBlank(request.password())) {
            errors.add("비밀번호는 필수 입력 값입니다.");
        }

        if (!errors.isEmpty()) {
            throw new InvalidMemberRequestException(errors); // 예외 클래스 변경
        }
    }

    private boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }
}
