package com.example.demo.exception;

public class PasswordException extends RuntimeException {
    public PasswordException() {
        super("패스워드를 입력하지 않았습니다");
    }
}
