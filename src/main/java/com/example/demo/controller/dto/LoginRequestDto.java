package com.example.demo.controller.dto;

import com.example.demo.validation.OnLogin;
import com.example.demo.validation.OnRegistration;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginRequestDto {
    @Size(groups = OnRegistration.class, min = 8, message = "회원가입 시 최초 유저 아이디는 8글자 이상이어야합니다")
    @Size(groups = OnLogin.class, min = 4, message = "유저 아이디는 4글자 이상이어야합니다")
    private final String username;
    @Size(groups = OnRegistration.class, min = 8, message = "회원가입 시 최초 유저 패스워드는 8글자 이상이어야합니다")
    @Size(groups = OnLogin.class, min = 4, message = "유저 패스워드는 4글자 이상이어야합니다")
    private final String password;
}
