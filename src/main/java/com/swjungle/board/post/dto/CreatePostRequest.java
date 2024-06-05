package com.swjungle.board.post.dto;

import java.time.LocalDateTime;

record CreatePostRequest(String title, String content, String link, String category, String score, String author, String password) {}
