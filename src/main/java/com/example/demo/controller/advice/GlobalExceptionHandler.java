package com.example.demo.controller.advice;

import com.example.demo.controller.dto.LoginResponseDto;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(CustomException.class)
    public LoginResponseDto handle(CustomException exception) {
        ErrorType type = exception.getType();
        log.makeLoggingEventBuilder(type.getLevel())
                .log(exception.getMessage(), exception);
        return LoginResponseDto.failed(type);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public LoginResponseDto handle(Exception exception) {
        log.error(exception.getMessage(), exception);
        return LoginResponseDto.failed(exception.getMessage());
    }
}
