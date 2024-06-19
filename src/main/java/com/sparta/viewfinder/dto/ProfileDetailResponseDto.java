package com.sparta.viewfinder.dto;

import com.sparta.viewfinder.entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProfileDetailResponseDto {
    private Long id;
    private Long userId;
    private String name;
    private String headline;
    private String email;
    private String phoneNumber;
    private String sns;

    public ProfileDetailResponseDto(Profile profile) {
        this.id = profile.getId();
        this.userId = profile.getUser().getId();
        this.name = profile.getName();
        this.headline = profile.getHeadline();
        this.email = profile.getEmail();
        this.phoneNumber = profile.getPhoneNumber();
        this.sns = profile.getSns();
    }
}