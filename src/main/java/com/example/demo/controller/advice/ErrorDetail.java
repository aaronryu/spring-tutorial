package com.example.demo.controller.advice;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_ABSENT)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorDetail {
    private final LocalDateTime timestamp;
    private final Integer status;
    private final String error;
    private final Object detail;
    private final String path;
}
