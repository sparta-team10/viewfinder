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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // 댓글 생성
    @PostMapping("/comments")
    public ResponseEntity<CommentResponseDto> createComment(@RequestParam Long postId, @RequestParam Long userId, @RequestBody CommentRequestDto commentRequestDto) {
        CommentResponseDto res = commentService.createComment(userId, postId, commentRequestDto);
        return ResponseEntity.ok(res);
    }

    // 게시물의 댓글 조회
    @GetMapping("/comments")
    public ResponseEntity<Object> readComment(@RequestParam Long postId) {
        List<CommentResponseDto> res = commentService.readComment(postId);
        if (res.isEmpty()) {
            return ResponseEntity.ok("There are no comments.");
        } else {
            return ResponseEntity.ok(res);
        }
    }

    // 댓글 수정
    @PatchMapping("/comments/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long id, @RequestParam Long userId, @RequestBody CommentRequestDto commentRequestDto) {
        CommentResponseDto res = commentService.updateComment(userId, id, commentRequestDto);
        return ResponseEntity.ok(res);
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id, @RequestParam Long userId) {
        return ResponseEntity.ok(commentService.deleteComment(userId, id));
    }
}