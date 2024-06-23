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

    public User(String username, String password, String name, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.status = UserStatusEnum.USER;
        this.userRole = UserRoleEnum.USER; // -> 회원가입할 때 어드민 or 일반 유저로 선택되는걸로 바꿔야함
        this.profile = new Profile(this);
    }

    public void withDraw() {
        this.status = UserStatusEnum.NON_USER;
        this.statusUpdate = this.getModifiedAt();
        this.refreshToken = null;
    }


    public boolean logout(){
        refreshToken = null;
        return refreshToken == null ? true : false;
    }

    public void saveRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
