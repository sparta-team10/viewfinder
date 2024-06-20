package com.sparta.viewfinder.dto;

import com.sparta.viewfinder.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter

public class CommentResponseDto {
    private  String username;
    private  Long postId;
    private  String content;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;


    //시간 String으로 바꾸는거 해결해야함.
    public CommentResponseDto(Comment comment){
        this.username = comment.getUser().getUsername();
        this.postId = comment.getPost().getId();
        this.content = comment.getContent();
        this.createAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}