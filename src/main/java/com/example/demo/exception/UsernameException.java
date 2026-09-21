package com.example.demo.exception;

public class UsernameException extends RuntimeException {
    public UsernameException() {
        super("유저 아이디를 입력하지 않았습니다");
    }
}
