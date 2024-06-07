package com.swjungle.board.post.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.swjungle.board.post.entity.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record PostResponse(
        @JsonProperty("id") Long id,
        @JsonProperty("title") String title,
        @JsonProperty("content") String content,
        @JsonProperty("link") String link,
        @JsonProperty("category") String category,
        @JsonProperty("score") int score,
        @JsonProperty("author") String author,
        @JsonProperty("created_at") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime createdAt,
        @JsonProperty("updated_at") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime updatedAt
) {

    public static PostResponse fromEntity(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getLink(),
                post.getCategory(),
                post.getScore(),
                post.getAuthor(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }

    public static List<PostResponse> valueOf(List<Post> postList){

        /* 정적 팩토리 메서드 */
        return postList.stream()
                .map(PostResponse::fromEntity)
                .collect(Collectors.toList());

        /* 생성자 사용 */
//        return postList.stream()
//                .map(post -> new PostResponse(
//                        post.getId(),
//                        post.getTitle(),
//                        post.getContent(),
//                        post.getLink(),
//                        post.getCategory(),
//                        post.getScore(),
//                        post.getAuthor(),
//                        post.getCreatedAt(),
//                        post.getUpdatedAt()
//                ))
//                .collect(Collectors.toList());
    }
}