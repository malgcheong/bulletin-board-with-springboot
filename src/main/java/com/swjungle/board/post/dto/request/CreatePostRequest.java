package com.swjungle.board.post.dto.request;

public record CreatePostRequest(String title, String content, String link, String category, int score, String author, String password) {}
