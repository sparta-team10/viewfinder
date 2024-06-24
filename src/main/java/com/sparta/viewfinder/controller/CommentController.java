package com.sparta.viewfinder.controller;

import com.sparta.viewfinder.dto.CommentRequestDto;
import com.sparta.viewfinder.dto.CommentResponseDto;
import com.sparta.viewfinder.entity.User;
import com.sparta.viewfinder.security.UserDetailsImpl;
import com.sparta.viewfinder.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

  private final CommentService commentService;
  private static final String DELETE_COMMENT_SUCCESS_MESSAGE = "댓글이 삭제되었습니다";
  private static final String NOT_FOUND_COMMENT = "댓글을 찾을 수 없습니다";

  // 댓글 생성
  @PostMapping
  public ResponseEntity<CommentResponseDto> createComment(
        @RequestParam Long postId,
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody CommentRequestDto requestDto) {
    Long userId = getUserIdByUserDetails(userDetails);
    CommentResponseDto res = commentService.createComment(userId, postId, requestDto);
    return ResponseEntity.ok(res);
  }

  // 게시물의 댓글 조회
  @GetMapping
  public ResponseEntity<Object> readComment(@RequestParam Long postId) {
    List<CommentResponseDto> res = commentService.readComment(postId);
    if (res.isEmpty()) {
      return ResponseEntity.ok().body(NOT_FOUND_COMMENT);
    } else {
      return ResponseEntity.ok(res);
    }
  }

  // 댓글 수정
  @PatchMapping("/{id}")
  public ResponseEntity<CommentResponseDto> updateComment(
        @PathVariable Long id,
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody CommentRequestDto requestDto) {
    Long userId = getUserIdByUserDetails(userDetails);
    CommentResponseDto res = commentService.updateComment(userId, id, requestDto);
    return ResponseEntity.ok(res);
  }

  // 댓글 삭제
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteComment(
        @PathVariable Long id,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
    Long userId = getUserIdByUserDetails(userDetails);
    commentService.deleteComment(id, userId);
    return ResponseEntity.ok().body(DELETE_COMMENT_SUCCESS_MESSAGE);
  }

  // 사용자 id get
  private Long getUserIdByUserDetails(UserDetailsImpl userDetails) {
    return userDetails.getUser().getId();
  }
}