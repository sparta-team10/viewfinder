package com.sparta.viewfinder.controller;

import com.sparta.viewfinder.dto.PostRequestDto;
import com.sparta.viewfinder.dto.PostResponseDto;
import com.sparta.viewfinder.security.UserDetailsImpl;
import com.sparta.viewfinder.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService service;
    private static final String DELTE_POST = "게시글이 삭제 되었습니다.";

    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@AuthenticationPrincipal UserDetailsImpl userDetails, @Valid @RequestBody PostRequestDto postRequestDto) {
        PostResponseDto postResponseDto = service.createPost(userDetails, postRequestDto);
        return ResponseEntity.ok(postResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> readAllPost() {
        List<PostResponseDto> postResponseDtoList = service.readAllPost();
        return ResponseEntity.ok(postResponseDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> readPost(@PathVariable Long id) {
        PostResponseDto postResponseDto = service.readPost(id);
        return ResponseEntity.ok(postResponseDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long id,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                      @Valid @RequestBody PostRequestDto postRequestDto)
    { //인증 인가 구현시 userId 변경// -> 토큰으로 가져오면 쉽게 해결
        PostResponseDto postResponseDto = service.updatePost(id, userDetails, postRequestDto);
        return ResponseEntity.ok(postResponseDto);
    }

//    @PatchMapping("/{id}")
//    public ResponseEntity<PostResponseDto> updatePost2(
//            @PathVariable Long id,
//            @RequestParam Long userId,
//            @RequestBody PostRequestDto postRequestDto)
//    { //인증 인가 구현시 userId 변경// -> 토큰으로 가져오면 쉽게 해결
//        PostResponseDto postResponseDto = service.updatePost(id, userId, postRequestDto);
//        return ResponseEntity.ok(postResponseDto);
//    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        //인증 인가 구현시 userId 변경 -> 토큰으로 가져오면 쉽게 해결
        service.deletePost(id, userDetails);
        return ResponseEntity.ok().body(DELTE_POST);
    }
}
