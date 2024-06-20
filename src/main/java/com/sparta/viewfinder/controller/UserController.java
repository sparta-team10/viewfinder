package com.sparta.viewfinder.controller;


import com.sparta.viewfinder.dto.LoginRequestDto;
import com.sparta.viewfinder.dto.UserRequestDto;
import com.sparta.viewfinder.dto.UserResponseDto;
import com.sparta.viewfinder.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입
    @PostMapping("/sign-up") // http://localhost:8080/sign-up POST
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto request) { // Valid를 달아야 검증 작동

        return ResponseEntity.ok(userService.createUser(request));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDto requestDto) {
        userService.login(requestDto);
        return ResponseEntity.ok().body("로그인 성공");
    }

    @PutMapping("/logout/{id}")
    public ResponseEntity<String> logout(@PathVariable Long id) {
        userService.logout(id);
        return ResponseEntity.ok().body("로그아웃 성공");
    }
}
