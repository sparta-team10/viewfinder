package com.sparta.viewfinder.dto;

import com.sparta.viewfinder.entity.Post;
import com.sparta.viewfinder.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRequestDto {
    @NotBlank
    private String content;
}
