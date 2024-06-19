package com.sparta.viewfinder.entity;

import com.sparta.viewfinder.dto.UserRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "user")
public class User extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String name;

    private String email;

    private UserStatusEnum status;

    private String refreshToken;

    private String statusUpdate;



    public User(UserRequestDto requestDto) {
        this.username = requestDto.getUserName();
        this.password = requestDto.getPassword();
        this.name = requestDto.getName();
        this.email = requestDto.getEmail();
    }
}
