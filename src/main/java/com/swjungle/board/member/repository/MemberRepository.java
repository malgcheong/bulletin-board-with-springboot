package com.swjungle.board.member.repository;

import com.swjungle.board.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

// MemberRepository
public interface MemberRepository extends JpaRepository<Member, Long> { // 기본 키 타입은 Long 유지
    Optional<Member> findByUsername(String username);
    Optional<Member> findByUuid(UUID uuid);
}