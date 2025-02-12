package com.example.demo.service;

import com.example.demo.exception.CustomException;
import com.example.demo.exception.ExceptionType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public enum JobType {
    DEVELOPER("개발자", Arrays.asList("Backend", "Frontend")),
    ENGINEER("엔지니어", Arrays.asList("DevOps", "SRE", "Security"));

    @JsonValue
    private final String name;
    private final List<String> specialties;

//    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
//    public JobType findByValue(String value) {
//        for (JobType each : JobType.values()) {
//            if (each.name().equals(value)) {
//                return each;
//            }
//        }
//        throw new CustomException(ExceptionType.ENUM_NOT_FOUND, "찾을 수 없는 JobType 입니다. 입력 : " + value);
//    }
}
