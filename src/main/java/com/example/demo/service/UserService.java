package com.example.demo.service;

import com.example.demo.controller.dto.UserCreateRequestDto;
import com.example.demo.controller.dto.UserResponseDto;
import com.example.demo.repository.Message;
import com.example.demo.repository.MessageJdbcApiRepository;
import com.example.demo.repository.User;
import com.example.demo.repository.UserJdbcApiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserJdbcApiRepository userJdbcApiRepository;
    private final MessageJdbcApiRepository messageJdbcApiRepository;

    public UserResponseDto findById(Integer id) throws SQLException {
        User retrievedUser = userJdbcApiRepository.findById(id);
        List<Message> retrievedMessages = messageJdbcApiRepository.findByUserId(id);
        return UserResponseDto.from(retrievedUser, retrievedMessages);
    }

    public UserResponseDto create(UserCreateRequestDto request) throws SQLException {
        User createdUser = userJdbcApiRepository.create(request.getName(), request.getAge(), request.getJob(), request.getSpecialty());
        Message createdMessages = messageJdbcApiRepository.create(createdUser.getId(), createdUser.getName() + "님 회원가입 감사드립니다!");
        return UserResponseDto.from(createdUser, Collections.singletonList(createdMessages));
    }
}
