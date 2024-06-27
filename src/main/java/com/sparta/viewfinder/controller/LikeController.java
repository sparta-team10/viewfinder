package com.sparta.viewfinder.controller;

import com.sparta.viewfinder.dto.LikeResponseDto;
import com.sparta.viewfinder.security.UserDetailsImpl;
import com.sparta.viewfinder.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/likes")
@RestController
public class LikeController {
  private final LikeService likeService;

  private static final String LIKE_CANCEL = "좋아요 취소";

  @PostMapping("/{contentId}")
  public ResponseEntity<LikeResponseDto> like(
      @PathVariable long contentId,
      @RequestParam("contentType") String contentType,
      @AuthenticationPrincipal UserDetailsImpl userDetails){

    LikeResponseDto responseDto = likeService.createLike(
        userDetails.getUser().getId(), contentId, contentType);
    return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
  }

  @DeleteMapping("/{likeId}")
  public ResponseEntity<String> deleteLike(
      @PathVariable long likeId, @AuthenticationPrincipal UserDetailsImpl userDetails){

    likeService.deleteLike(likeId, userDetails.getUser().getId());
    return ResponseEntity.ok().body(LIKE_CANCEL);
  }

}
