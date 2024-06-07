package com.swjungle.board.post.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PostWrapperResponse(
        @JsonProperty("post")
        PostResponse postResponse
) {}
