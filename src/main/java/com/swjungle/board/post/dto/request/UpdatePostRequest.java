package com.swjungle.board.post.dto.request;

public record UpdatePostRequest(String title, String content, String link, String category, int score) {
}
