package com.example.demo.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserCreateRequestDto {
    private String name;
    private Integer age;
    private String job;
    private String specialty = "EMPTY";
}
