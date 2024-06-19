package com.sparta.viewfinder.dto;

import com.sparta.viewfinder.entity.UserStatusEnum;
import jakarta.persistence.Entity;
import lombok.Getter;
@Getter

public class UserRequestDto {
    private String userName;

    private String password;

    private String name;

    private String email;

}
