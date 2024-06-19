package com.sparta.viewfinder.controller;

import com.sparta.viewfinder.dto.CommentRequestDto;
import com.sparta.viewfinder.dto.CommentResponseDto;
import com.sparta.viewfinder.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // 임시 user
    private final Long userId = 10L;

    // 댓글 생성
    @PostMapping("/")
    public ResponseEntity<CommentResponseDto> createComment(@RequestParam Long postId, @RequestBody CommentRequestDto commentRequestDto) {
        CommentResponseDto res = commentService.createComment(userId, postId, commentRequestDto);
        return ResponseEntity.ok(res);
    }

    // 게시물의 댓글 조회
    @GetMapping("/")
    public ResponseEntity<List<CommentResponseDto>> readComment(@RequestParam Long postId) {
        List<CommentResponseDto> res = commentService.readComment(postId);
        return ResponseEntity.ok(res);
    }

    // 댓글 수정
    @PatchMapping("/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto) throws IllegalAccessException {
        CommentResponseDto res = commentService.updateComment(userId, id, commentRequestDto);
        return ResponseEntity.ok(res);
    }

    // 댓글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteComment(@PathVariable Long id) throws IllegalAccessException {
        return ResponseEntity.ok(commentService.deleteComment(userId, id));
    }
}