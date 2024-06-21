package com.sparta.viewfinder.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,
            "해당 유저가 없습니다."),
    DUPLICATED_USER(HttpStatus.NOT_FOUND,
                    "중복된 유저입니다."),
    USER_NOT_MATCH(HttpStatus.NOT_FOUND,
            "유저가 일치하지 않습니다."),
    PATTERN_NOT_MATCH(HttpStatus.NOT_FOUND,
            "비밀번호 패턴이 일치하지 않습니다."),
    WITHDRAW_USER(HttpStatus.NOT_FOUND,
        "탈퇴한 회원입니다.");

    private final HttpStatus status;
    private final String message;

    @Override
    public HttpStatus getHttpStatus() {
        return this.status;
    }
}
