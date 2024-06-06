package com.swjungle.board.post.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record PostWrapperListResponse(
        @JsonProperty("posts")
        List<PostResponse> postResponse
) {}