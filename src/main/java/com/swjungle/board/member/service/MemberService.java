package com.swjungle.board.member.service;

import com.swjungle.board.common.security.JwtTokenProvider;
import com.swjungle.board.member.dto.request.LoginRequest;
import com.swjungle.board.member.dto.request.SignupRequest;
import com.swjungle.board.member.entity.Member;
import com.swjungle.board.member.exception.DuplicateMemberException;
import com.swjungle.board.member.exception.InvalidPasswordException;
import com.swjungle.board.member.exception.MemberNotFoundException;
import com.swjungle.board.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRequestValidator memberRequestValidator;

    public void signup(SignupRequest request) {
        memberRequestValidator.validate(request); // 유효성 검사

        if (memberRepository.findByUsername(request.username()).isPresent()) {
            throw new DuplicateMemberException(request.username()); // 중복 회원 예외 처리
        }
        Member member = Member.of(UUID.randomUUID(), request.username(), passwordEncoder.encode(request.password()), Member.Role.USER);
        memberRepository.save(member);
    }

    public String login(LoginRequest request) {
        memberRequestValidator.validate(request); // 유효성 검사

        Member member = memberRepository.findByUsername(request.username())
                .orElseThrow(MemberNotFoundException::new); // 회원 없음 예외 처리

        if (!passwordEncoder.matches(request.password(), member.getPassword())) {
            throw new InvalidPasswordException(); // 비밀번호 불일치 예외 처리
        }

        String token = jwtTokenProvider.createToken(member.getUuid().toString(), member.getRole()); // JWT 토큰 생성
        return token;
    }
}
