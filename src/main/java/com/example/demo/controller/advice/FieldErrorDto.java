package com.example.demo.controller.advice;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.FieldError;

import java.util.Objects;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class FieldErrorDto {
    private final String message;
    private final String field;
    private final String value;
    private final String code;

    public static FieldErrorDto from(FieldError error) {
        return new FieldErrorDto(
                error.getDefaultMessage(),
                error.getField(),
                Objects.requireNonNull(error.getRejectedValue()).toString(),
                error.getCode()
        );
    }
}
