package com.example.demo.service;

import com.example.demo.controller.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserJdbcApiDao userJdbcRepository;

    public UserResponseDto findById(Integer id) {
        try {
            User user = userJdbcRepository.findById(id);
            return UserResponseDto.from(user);
        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "자원 반납 시 문제가 있습니다.");
        }
    }

    public List<UserResponseDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserResponseDto::from)
                .toList();
    }

    public UserResponseDto save(String name, Integer age, String job, String specialty) {
        User user = userRepository.save(new User(null, name, age, job, specialty, LocalDateTime.now()));
        return UserResponseDto.from(user);
    }
}
