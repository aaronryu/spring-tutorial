package com.example.demo.controller.dto.common;

import com.example.demo.exception.ExceptionType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class BaseResponse<T> {
    private final boolean success;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String type;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T body;

    public static <T> BaseResponse<T> of(boolean success, String type, String message, T body) {
        return new BaseResponse<T>(success, type, message, body);
    }

    public static <T> BaseResponse<T> success(T body) {
        return new BaseResponse<T>(true, null, null, body);
    }

    public static <T> BaseResponse<T> failure(ExceptionType type) {
        return new BaseResponse<T>(false, type.getType(), type.getDesc(), null);
    }
}
