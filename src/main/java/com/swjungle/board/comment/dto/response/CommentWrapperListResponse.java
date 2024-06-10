package com.swjungle.board.comment.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.swjungle.board.post.dto.response.PostResponse;

import java.util.List;

public record CommentWrapperListResponse(
        @JsonProperty("comments")
        List<CommentResponse> commentResponse
) {}