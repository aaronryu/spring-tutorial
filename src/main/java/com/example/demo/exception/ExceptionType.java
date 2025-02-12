package com.example.demo.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.event.Level;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionType {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, Level.WARN),
    USER_NOT_CREATED(HttpStatus.INTERNAL_SERVER_ERROR, Level.ERROR),
    ENUM_NOT_FOUND(HttpStatus.NOT_FOUND, Level.WARN);

    private final HttpStatus status;
    private final Level level;
}
