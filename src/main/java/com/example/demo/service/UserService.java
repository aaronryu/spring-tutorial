package com.example.demo.service;

import com.example.demo.controller.user.dto.UserCreateRequestDto;
import com.example.demo.controller.user.dto.UserResponseDto;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public UserResponseDto findById(Integer id) {
        User retrieved = repository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND, "유저가 존재하지 않습니다. id : " + id));
        return UserResponseDto.from(retrieved);
    }

    public List<UserResponseDto> findAll() {
        List<User> retrieved = repository.findAll();
        return retrieved.stream().map(UserResponseDto::from).toList();
    }

    public UserResponseDto save(UserCreateRequestDto request) {
        User createEntity = request.toCreateEntity();
        User retrieved = repository.save(createEntity)
                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_CREATED, "유저가 저장되지 않았습니다. id : " + request));
        return UserResponseDto.from(retrieved);
    }
}
