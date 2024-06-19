package com.sparta.viewfinder.controller;


import com.sparta.viewfinder.dto.UserRequestDto;
import com.sparta.viewfinder.entity.User;
import com.sparta.viewfinder.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
//@RequestMapping("/sign-up")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // create
    @PostMapping("/sign-up") // http://localhost:8080/sign-up POST
    public ResponseEntity<User> createUser(@RequestBody UserRequestDto request) {
        // { cardNumber:" 16자리 "}
        return ResponseEntity.ok(userService.createUser(request));
    }


}
