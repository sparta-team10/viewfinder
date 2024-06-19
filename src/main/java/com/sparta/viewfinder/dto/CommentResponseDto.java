package com.sparta.viewfinder.dto;

import com.sparta.viewfinder.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {
    private final Long id;
    private final Long userId;
    private final Long postId;
    private final String content;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.userId = comment.getUserId();
        this.postId = comment.getPostId();
        this.content = comment.getContent();
    }
}