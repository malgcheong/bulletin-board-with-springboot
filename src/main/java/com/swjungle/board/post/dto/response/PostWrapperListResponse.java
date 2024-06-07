package com.swjungle.board.post.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record PostWrapperListResponse(
        @JsonProperty("posts")
        List<PostResponse> postResponse
) {}