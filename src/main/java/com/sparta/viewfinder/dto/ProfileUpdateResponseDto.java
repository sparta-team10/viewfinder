package com.sparta.viewfinder.dto;

import com.sparta.viewfinder.entity.Profile;
import lombok.Getter;

@Getter
public class ProfileUpdateResponseDto {
    private final String headline;
    private final String phoneNumber;
    private final String sns;

    public ProfileUpdateResponseDto(Profile profile) {
        this.headline = profile.getHeadline();
        this.phoneNumber = profile.getPhoneNumber();
        this.sns = profile.getSns();
    }
}
