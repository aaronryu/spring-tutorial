package com.example.demo.controller.user.dto;

import com.example.demo.service.JobType;
import com.example.demo.service.User;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@AllArgsConstructor
public class UserCreateRequestDto {
    private String name;
    @Min(10)
    private Integer age;
    private JobType job;
    private String specialty;

    public User toCreateEntity() {
        return new User(null, name, age, job, specialty, LocalDateTime.now());
    }
}
