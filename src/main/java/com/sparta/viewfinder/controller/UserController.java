package com.sparta.viewfinder.controller;

import com.sparta.viewfinder.dto.LoginRequestDto;
import com.sparta.viewfinder.dto.UserRequestDto;
import com.sparta.viewfinder.dto.UserResponseDto;
import com.sparta.viewfinder.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@RestController
public class UserController {

    private final UserService userService;
    private static final String SUCCESS_LOGIN = "로그인 성공";
    private static final String SUCCESS_LOGOUT = "로그아웃 성공";
    private static final String SUCCESS_SIGN_UP = "회원가입에 성공하였습니다.";


    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입
    @PostMapping("/sign-up") // http://localhost:8080/sign-up POST
    public ResponseEntity<String> createUser(@Valid @RequestBody UserRequestDto request) { // Valid를 달아야 검증 작동
        userService.createUser(request);
        return ResponseEntity.ok().body(SUCCESS_SIGN_UP);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDto requestDto) {
        userService.login(requestDto);
        return ResponseEntity.ok().body(SUCCESS_LOGIN);
    }

    @PutMapping("/logout/{id}")
    public ResponseEntity<String> logout(@PathVariable Long id) {
        userService.logout(id);
        return ResponseEntity.ok().body(SUCCESS_LOGOUT);
    }
}
