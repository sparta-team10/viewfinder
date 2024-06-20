package com.sparta.viewfinder.controller;

import com.sparta.viewfinder.dto.ProfileAllResponseDto;
import com.sparta.viewfinder.dto.ProfileDetailResponseDto;
import com.sparta.viewfinder.dto.ProfileUpdateRequestDto;
import com.sparta.viewfinder.dto.ProfileUpdateResponseDto;
import com.sparta.viewfinder.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping("/users")
    public ResponseEntity<List<ProfileAllResponseDto>> getAllProfiles() {
        return ResponseEntity.status(HttpStatus.OK).body(
                profileService.getAllProfiles()
        );
    }

    @GetMapping("/users/{profile_id}")
    public ResponseEntity<ProfileDetailResponseDto> getProfileDetail(@PathVariable("profile_id") Long profileId) {
        return ResponseEntity.status(HttpStatus.OK).body(profileService.getProfileDetail(profileId));
    }

    @PatchMapping("/users/{user_id}") // Jwt 구현되면 {user_id} 제거할 예정
    public ResponseEntity<ProfileUpdateResponseDto> updateProfile(@PathVariable("user_id") Long userId, @RequestBody ProfileUpdateRequestDto profileUpdateRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(profileService.updateProfile(userId, profileUpdateRequestDto));
    }

}
