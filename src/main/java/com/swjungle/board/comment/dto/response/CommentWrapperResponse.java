package com.swjungle.board.comment.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CommentWrapperResponse(
        @JsonProperty("comment")
        CommentResponse commentResponse
) {}
