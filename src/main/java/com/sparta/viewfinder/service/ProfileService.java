package com.sparta.viewfinder.service;

import com.sparta.viewfinder.dto.ProfileAllResponseDto;
import com.sparta.viewfinder.dto.ProfileDetailResponseDto;
import com.sparta.viewfinder.entity.Profile;
import com.sparta.viewfinder.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;

    public List<ProfileAllResponseDto> getAllProfiles() {
        List<Profile> profileAllResponseDtoList = profileRepository.findAll();
        return profileAllResponseDtoList.stream().map(ProfileAllResponseDto::new).toList();
    }

    public ProfileDetailResponseDto getProfileDetail(Long profileId) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        return new ProfileDetailResponseDto(profile);
    }
}
