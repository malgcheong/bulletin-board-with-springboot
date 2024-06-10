package com.swjungle.board.post.service;

import com.swjungle.board.post.dto.request.CreatePostRequest;
import com.swjungle.board.post.dto.request.UpdatePostRequest;
import com.swjungle.board.post.exception.InvalidPostRequestException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PostRequestValidator {

    public void validate(CreatePostRequest request) {
        List<String> errors = new ArrayList<>();

        // title 검증
        if (isBlank(request.title())) {
            errors.add("제목은 필수 입력 값입니다.");
        }

        // content 검증
        if (isBlank(request.content())) {
            errors.add("내용은 필수 입력 값입니다.");
        }

        // author 검증
        if (isBlank(request.author())) {
            errors.add("작성자는 필수 입력 값입니다.");
        }

        if (!errors.isEmpty()) {
            throw new InvalidPostRequestException(errors);
        }
    }

    public void validate(UpdatePostRequest request) {
        // UpdatePostRequest에 대한 검증 로직 추가 (필요한 경우)
        // 위의 validate(CreatePostRequest) 메서드와 비슷한 방식으로 구현
        List<String> errors = new ArrayList<>();

        // title 검증
        if (isBlank(request.title())) {
            errors.add("제목은 필수 입력 값입니다.");
        }

        // content 검증
        if (isBlank(request.content())) {
            errors.add("내용은 필수 입력 값입니다.");
        }

        if (!errors.isEmpty()) {
            throw new InvalidPostRequestException(errors);
        }
    }

    private boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }
}