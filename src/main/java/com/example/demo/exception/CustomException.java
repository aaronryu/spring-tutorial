package com.example.demo.exception;

public class CustomException extends RuntimeException {
    private final ErrorType type;

    public CustomException(ErrorType type) {
        super(type.getMessage());
        this.type = type;
    }
}
