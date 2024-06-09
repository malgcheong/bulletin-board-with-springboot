package com.swjungle.board.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA는 기본 생성자가 필요합니다.
@EntityListeners(AuditingEntityListener.class)
@Table(name = "member")
public class Member {
    @Column(nullable = false, unique = true)
    private  UUID uuid;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    static public Member of(UUID uuid, String username, String password, Role role){
        return Member.builder().uuid(uuid).username(username).password(password).role(role).build();
    }

    @Builder
    public Member(UUID uuid, String username, String password, Role role, LocalDateTime createdAt, LocalDateTime updatedAt){
        this.uuid = uuid;
        this.username = username;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public enum Role {
        USER, // 일반 사용자
        ADMIN // 관리자
    }
}


