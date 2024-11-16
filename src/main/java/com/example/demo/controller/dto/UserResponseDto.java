package com.example.demo.controller.dto;

import com.example.demo.service.Message;
import com.example.demo.service.User;
import com.fasterxml.jackson.annotation.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

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
    private String job;
    private String specialty;
    @DateFormat
    private LocalDateTime createdAt;
    private String address;
    private String postcode;
    private List<MessageResponseDto> messages;

    public static UserResponseDto from(User entity) {
        return new UserResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getAge(),
                entity.getJob(),
                entity.getSpecialty(),
                entity.getCreatedAt(),
                null,
                null,
                null
        );
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages.stream()
                .map(MessageResponseDto::from)
                .toList();
    }
}
