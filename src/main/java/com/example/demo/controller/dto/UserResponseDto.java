package com.example.demo.controller.dto;

import com.example.demo.service.User;
import com.fasterxml.jackson.annotation.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"userId", "username"})
public class UserResponseDto {
    @JsonProperty("userId")
    private Integer id;
    @JsonProperty("username")
    private String name;
    @JsonIgnore
    private Integer age;
    private JobType job;
    private String specialty;
    @DateFormat
    private LocalDateTime createdAt;
    private String address;
    private String postcode;

    public static UserResponseDto from(User entity) {
        return new UserResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getAge(),
                entity.getJob(),
                entity.getSpecialty(),
                entity.getCreatedAt(),
                null,
                null
        );
    }
}
