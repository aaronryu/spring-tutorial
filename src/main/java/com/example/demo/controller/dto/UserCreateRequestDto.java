package com.example.demo.controller.dto;

import com.example.demo.repository.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserCreateRequestDto {
    private String name;
    private Integer age;
    private String job;
    private String specialty = "EMPTY";

    public User toCreating() {
        return new User(null, this.name, this.age, this.job, this.specialty, LocalDateTime.now(), null);
    }
}
