package com.example.demo.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public enum JobType {
    DEVELOPER("Developer", Arrays.asList("Frontend", "Backend")),
    ENGINEER("Engineer", Arrays.asList("DevOps", "SRE"));

    String name;
    List<String> titles;
}
