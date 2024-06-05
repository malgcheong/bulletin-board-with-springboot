package com.swjungle.board.post.dto;

import java.time.LocalDateTime;

public record PostResponse(Long id, String title, String content, String link, String category, int score, String author, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
