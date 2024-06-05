package com.swjungle.board.post.dto;

public record updatePostRequest(String title, String content, String link, String category, String score, String password) {
}
