package com.sparta.viewfinder.dto;

import com.sparta.viewfinder.entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProfileAllResponseDto {
    private String name;
    private String headline;

    public ProfileAllResponseDto(Profile profile) {
        this.name = profile.getUser().getName();
        this.headline = profile.getHeadline();
    }
}
