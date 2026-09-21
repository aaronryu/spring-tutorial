package com.example.demo.controller.advice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ToString
@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class InvalidParameterDto {
    private final String parameter;           // id
    private final Object actualValue;         // 0
    private final Object criteriaValue;       // 1
    private final String criteria;            // Max
    private final String violationMessage;    // 1 이상의 값이 들어가야합니다
}