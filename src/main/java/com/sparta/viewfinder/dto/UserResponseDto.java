package com.sparta.viewfinder.dto;

import com.sparta.viewfinder.entity.User;
import lombok.Getter;


//이름 변경 signUpResponseDto
@Getter
public class UserResponseDto {
    private String username;
    private String name;
    private String email;
//    private String creatAt;

    public UserResponseDto(User user) {
        this.username = user.getUsername();
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
