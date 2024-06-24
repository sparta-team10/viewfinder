package com.sparta.viewfinder.controller;

import com.sparta.viewfinder.dto.*;
import com.sparta.viewfinder.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<List<ProfileAllResponseDto>> getAllProfiles() {
        List<ProfileAllResponseDto> allProfiles = profileService.getAllProfiles();
        return ResponseEntity.ok().body(allProfiles);
    }

    @GetMapping("/{profile_id}")
    public ResponseEntity<ProfileDetailResponseDto> getProfileDetail(@PathVariable("profile_id") Long profileId) {
        ProfileDetailResponseDto profileDetail = profileService.getProfileDetail(profileId);
        return ResponseEntity.ok().body(profileDetail);
    }

    @PatchMapping("/{user_id}") // Jwt 구현되면 {user_id} 제거할 예정
    public ResponseEntity<ProfileUpdateResponseDto> updateProfile(
            @PathVariable("user_id") Long userId,
            @RequestBody ProfileUpdateRequestDto requestDto) {
        ProfileUpdateResponseDto profileUpdateResponseDto = profileService.updateProfile(userId, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(profileUpdateResponseDto);
    }
}
