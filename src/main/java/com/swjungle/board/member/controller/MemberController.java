package com.swjungle.board.member.controller;

import com.swjungle.board.common.dto.EnvelopeResponse;
import com.swjungle.board.common.dto.MessageResponse;
import com.swjungle.board.member.dto.request.LoginRequest;
import com.swjungle.board.member.dto.request.SignupRequest;
import com.swjungle.board.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<EnvelopeResponse<MessageResponse>> signup(@RequestBody SignupRequest request) {
        memberService.signup(request);
        return ResponseEntity.ok().body(EnvelopeResponse.success(new MessageResponse("회원가입 성공")));
    }

    @PostMapping("/login")
    public ResponseEntity<EnvelopeResponse<MessageResponse>> login(@RequestBody LoginRequest request) {
        String token = memberService.login(request);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(EnvelopeResponse.success(new MessageResponse("로그인 성공")));
    }
}
