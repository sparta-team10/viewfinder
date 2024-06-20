package com.sparta.viewfinder.dto;

import com.sparta.viewfinder.entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProfileDetailResponseDto {
    private String name;
    private String headline;
    private String email;
    private String phoneNumber;
    private String sns;
    //    private String createAt;


    public ProfileDetailResponseDto(Profile profile) {
        this.name = profile.getUser().getName();
        this.headline = profile.getHeadline();
        this.email = profile.getUser().getEmail();
        this.phoneNumber = profile.getPhoneNumber();
        this.sns = profile.getSns();
    }
}