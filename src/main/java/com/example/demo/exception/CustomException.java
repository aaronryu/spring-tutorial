package com.example.demo.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final ErrorType type;

    public CustomException(ErrorType type) {
        super(type.getMessage());
        this.type = type;
    }
}
