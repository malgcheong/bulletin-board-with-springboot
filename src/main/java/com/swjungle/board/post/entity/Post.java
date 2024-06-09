package com.swjungle.board.post.entity;

import com.swjungle.board.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
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

    // Member와의 관계 설정 (JoinColumn 수정)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id") // member_id 컬럼으로 Member 엔티티의 id 참조
    private Member member;

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

    static public Post of(String title, String content, String link, String category, int score, String author, String password){
        return Post.builder()
                .title(title).content(content)
                .link(link).category(category)
                .score(score).author(author)
                .password(password).build();
    }

    @Builder
    public Post(Long id, String title, String content, String link, String category, int score, String author, String password, LocalDateTime createdAt, LocalDateTime updatedAt){
        this.id = id;
        this.title = title;
        this.content = content;
        this.link = link;
        this.category = category;
        this.score = score;
        this.author = author;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void update(String title, String content, String link, String category, int score, String author, String password){
        this.title = title;
        this.content = content;
        this.link = link;
        this.category = category;
        this.score = score;
        this.author = author;
        this.password = password;
    }

}

