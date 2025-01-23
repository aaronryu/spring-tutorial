package com.example.demo.controller.dto;

import com.example.demo.repository.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserResponseDto {
    private Integer id;
    private String username;
    private String name;
    private String job;
    private String specialty;

    public static UserResponseDto from(User entity) {
        return new UserResponseDto(
                entity.getId(),
                entity.getUsername(),
                entity.getName(),
                entity.getJob(),
                entity.getSpecialty()
        );
    }
}