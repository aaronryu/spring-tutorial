package com.example.demo.service;

import com.example.demo.controller.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserJdbcApiDao userJdbcRepository;
    private final UserJdbcTemplateDao userJdbcTemplateRepository;

    public UserResponseDto findById(Integer id) {
        User user = userJdbcTemplateRepository.findById(id);
        return UserResponseDto.from(user);
    }

    public List<UserResponseDto> findAll() {
        return userJdbcTemplateRepository.findAll()
                .stream()
                .map(UserResponseDto::from)
                .toList();
    }

    public UserResponseDto save(String name, Integer age, String job, String specialty) {
        User user = userJdbcTemplateRepository.save(name, age, job, specialty);
        return UserResponseDto.from(user);
    }

    public UserResponseDto update(Integer id, String name, Integer age, String job, String specialty) {
        User user = userJdbcTemplateRepository.update(id, name, age, job, specialty);
        return UserResponseDto.from(user);
    }

    public void delete(Integer id) {
        userJdbcTemplateRepository.delete(id);
    }
}
