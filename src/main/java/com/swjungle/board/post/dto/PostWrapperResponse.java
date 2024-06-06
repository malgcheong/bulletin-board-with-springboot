package com.swjungle.board.post.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PostWrapperResponse(
        @JsonProperty("post")
        PostResponse postResponse
) {}
