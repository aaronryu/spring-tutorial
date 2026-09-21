package com.example.demo.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public enum ErrorType {
    USERNAME_NOT_EXIST("유저 아이디를 입력하지 않았습니다"),
    PASSWORD_NOT_EXIST("패스워드를 입력하지 않았습니다"),
    AUTHENTICATION_FAILED("데이터베이스 접속에 실패했습니다 - 사유 : ID / PW 일치하지 않습니다");

    String message;
}
