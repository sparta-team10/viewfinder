package com.sparta.viewfinder.dto;

import com.sparta.viewfinder.entity.Profile;
import lombok.Getter;

@Getter
public class ProfileUpdateRequestDto {
    private String headline;
    private String phoneNumber;
    private String sns;
}
