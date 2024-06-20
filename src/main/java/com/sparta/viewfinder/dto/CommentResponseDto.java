package com.sparta.viewfinder.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentResponseDto {
    private final Long id;
    private final Long userId;
    private final Long postId;
    private final String content;
}