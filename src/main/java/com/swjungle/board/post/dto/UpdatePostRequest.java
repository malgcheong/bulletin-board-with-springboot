package com.swjungle.board.post.dto;

public record UpdatePostRequest(String title, String content, String link, String category, int score, String author, String password) {
}
