package com.swjungle.board.comment.entity;

import com.swjungle.board.member.entity.Member;
import com.swjungle.board.post.entity.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA는 기본 생성자가 필요합니다.
@EntityListeners(AuditingEntityListener.class)
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Member와의 관계 설정 (JoinColumn 수정)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id") // member_id 컬럼으로 Member 엔티티의 id 참조
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    // Post와의 관계 설정 (JoinColumn 수정)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", referencedColumnName = "id") // post_id 컬럼으로 Member 엔티티의 id 참조
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    static public Comment of(Member member, Post post, String content){
        return Comment.builder()
                .member(member).post(post).content(content).build();
    }

    @Builder
    public Comment(Member member, Post post, String content, LocalDateTime createdAt, LocalDateTime updatedAt){
        this.member = member;
        this.post = post;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void update(String content){
        this.content = content;
    }
    }
