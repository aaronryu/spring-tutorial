package com.example.demo.controller.dto;

import com.example.demo.exception.ErrorType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginResponseDto {
    private final boolean success;
    private final String username;
    private final String message;

    public static LoginResponseDto success(String username) {
        return new LoginResponseDto(true, username, "로그인이 성공되었습니다");
    }

    public static LoginResponseDto failed(ErrorType type) {
        return new LoginResponseDto(false, null, type.getMessage());
    }

    public static LoginResponseDto failed(String message) {
        return new LoginResponseDto(false, null, message);
    }
}
