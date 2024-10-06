package com.example.demo.controller.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FieldErrorDto {
    private String field;
    private Object invalidValue;
    private String message;
}
