package com.swjungle.board.post.dto;

public record UpdatePostRequest(String title, String content, String link, String category, String score, String password) {
}
