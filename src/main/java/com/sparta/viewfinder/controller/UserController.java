package com.sparta.viewfinder.controller;


import com.sparta.viewfinder.dto.LoginRequestDto;
import com.sparta.viewfinder.dto.UserRequestDto;
import com.sparta.viewfinder.entity.User;
import com.sparta.viewfinder.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입
    @PostMapping("/sign-up") // http://localhost:8080/sign-up POST
    public ResponseEntity<User> createUser(@RequestBody UserRequestDto request) {
        return ResponseEntity.ok(userService.createUser(request));
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDto requestDto) {
        userService.login(requestDto);
        return ResponseEntity.ok().body("로그인 성공");
    }

    @PutMapping("/logout/{id}")
    public ResponseEntity logout(@PathVariable Long id) {
        userService.logout(id);
        return ResponseEntity.ok().body("로그아웃 성공");
    }



}
