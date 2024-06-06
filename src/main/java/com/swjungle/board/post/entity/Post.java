package com.swjungle.board.post.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA는 기본 생성자가 필요합니다.
@EntityListeners(AuditingEntityListener.class)
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String link;

    private String category;

    private int score;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String password;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public Post(String title, String content, String link, String category, int score, String author, String password){
        this.title = title;
        this.content = content;
        this.link = link;
        this.category = category;
        this.score = score;
        this.author = author;
        this.password = password;
    }
}

