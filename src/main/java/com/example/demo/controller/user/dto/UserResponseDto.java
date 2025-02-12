package com.example.demo.controller.user.dto;

import com.example.demo.service.JobType;
import com.example.demo.service.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class UserResponseDto {
    private final Integer id;
    private final String name;
    private final Integer age;
    private final JobType job;
    private final String specialty;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년 MM월 dd일 HH시 mm분", timezone = "Asia/Seoul")
    private final LocalDateTime createdAt;

    public static UserResponseDto from(User entity) {
        return new UserResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getAge(),
                entity.getJob(),
                entity.getSpecialty(),
                entity.getCreatedAt()
        );
    }
}
