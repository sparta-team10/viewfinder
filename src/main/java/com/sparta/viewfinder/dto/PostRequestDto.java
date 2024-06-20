package com.sparta.viewfinder.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter

public class PostRequestDto {
    private String content;
}
