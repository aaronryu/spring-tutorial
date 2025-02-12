package com.example.demo.controller.advice;

import com.example.demo.exception.CustomException;
import com.example.demo.exception.ExceptionType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestControllerAdvice(basePackages = {"com.example.demo.controller.user"})
public class CustomUserExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorDetail> handle(HttpServletRequest request, CustomException e) {
        ExceptionType type = e.getType();
        HttpStatus status = type.getStatus();
        ErrorDetail detail = ErrorDetail.builder()
                .status(status.value())
                .path(request.getMethod() + " - " + request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .error(e.getMessage())
                .build();

        log.atLevel(type.getLevel()).setCause(e).log("[Custom] " + e.getMessage());
        return ResponseEntity
                .status(status)
                .body(detail);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDetail> handle(HttpServletRequest request, MethodArgumentNotValidException e) {
        StringBuilder stringBuilder = new StringBuilder();
        List<FieldErrorDto> errors = e.getFieldErrors().stream().map(FieldErrorDto::from).toList();
        for (FieldErrorDto error : errors) {
            String message = String.format("[%s에 들어온 값: %s <- %s] ", error.getField(), error.getValue(), error.getMessage());
            stringBuilder.append(message);
        }
        String messages = stringBuilder.toString();

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorDetail detail = ErrorDetail.builder()
                .status(status.value())
                .path(request.getMethod() + " - " + request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .error(messages)
                .detail(errors)
                .build();

        log.warn("[ArgumentNotValid] " + messages);
        return ResponseEntity
                .status(status)
                .body(detail);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDetail> handle(HttpServletRequest request, Exception e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorDetail detail = ErrorDetail.builder()
                .status(status.value())
                .path(request.getMethod() + " - " + request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .error(e.getMessage())
                .build();

        log.error("[Unclassified] " + e.getMessage(), e);
        return ResponseEntity
                .status(status)
                .body(detail);
    }
}
