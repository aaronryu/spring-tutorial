package com.example.demo.controller.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginRequestDto {
    @Size(min = 4, message = "유저 아이디는 4글자 이상이어야합니다")
    private final String username;
    @Size(min = 4, message = "유저 패스워드는 4글자 이상이어야합니다")
    private final String password;
}
