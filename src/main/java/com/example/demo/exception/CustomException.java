package com.example.demo.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final ExceptionType type;

    public CustomException(ExceptionType type, String message) {
        super(message);
        this.type = type;
    }
}
