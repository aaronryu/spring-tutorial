package com.example.demo.controller.dto;

import com.example.demo.service.Message;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"createdAt", "message"})
public class MessageResponseDto {
    @DateFormat
    private LocalDateTime createdAt;
    private String message;

    public static MessageResponseDto from(Message entity) {
        return new MessageResponseDto(
                entity.getCreatedAt(),
                entity.getMessage()
        );
    }
}
