package com.example.demo.controller.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginRequestDto {
    @Size(min = 4)
    private final String username;
    @Size(min = 4)
    private final String password;
}
