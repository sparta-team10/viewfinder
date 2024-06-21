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

    @Enumerated(EnumType.STRING)
    private UserStatusEnum status;

    private String refreshToken;

    private String statusUpdate;

    @Enumerated(EnumType.STRING)
    private UserRoleEnum userRole;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private Profile profile;



    public User(UserRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.password = requestDto.getPassword();
        this.name = requestDto.getName();
        this.email = requestDto.getEmail();
        this.status = UserStatusEnum.USER;
        this.userRole = UserRoleEnum.USER;
        this.profile = new Profile(this);
    }

    public boolean logout(){
        refreshToken = null;
        return refreshToken == null ? true : false;
    }

  public void saveRefreshToken(String refreshToken) {
      this.refreshToken = refreshToken;
  }
}
