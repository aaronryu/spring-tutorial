package com.example.demo.service;

import com.example.demo.controller.dto.JobType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@ToString
public class User {
    @Setter
    private Integer id;
    private String name;
    private Integer age;
    private JobType job;
    private String specialty;
    private LocalDateTime createdAt;
}
