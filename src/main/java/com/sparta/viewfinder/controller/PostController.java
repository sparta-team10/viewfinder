package com.sparta.viewfinder.controller;

import com.sparta.viewfinder.dto.PostRequestDto;
import com.sparta.viewfinder.dto.PostResponseDto;
import com.sparta.viewfinder.entity.User;
import com.sparta.viewfinder.service.PostService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService service;

    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@RequestParam Long id, @RequestBody PostRequestDto postRequestDto) {
        PostResponseDto postResponseDto = service.createPost(id, postRequestDto);
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
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long id, @RequestParam Long userId, @RequestBody PostRequestDto postRequestDto) { //인증 인가 구현시 userId 변경
        PostResponseDto postResponseDto = service.updatePost(id, userId, postRequestDto);
        return ResponseEntity.ok(postResponseDto);
    }

    // 댓글 삭제
    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id, @RequestParam Long userId) { //인증 인가 구현시 userId 변경
        service.deletePost(id, userId);
        //ResponseEntity.ok(service.deletePost(id, user.getId()));
    }
}
