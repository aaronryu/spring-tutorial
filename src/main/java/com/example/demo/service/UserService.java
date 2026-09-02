package com.example.demo.service;

import com.example.demo.controller.dto.UserCreateRequestDto;
import com.example.demo.controller.dto.UserResponseDto;
import com.example.demo.repository.Message;
import com.example.demo.repository.MessageJdbcApiRepository;
import com.example.demo.repository.User;
import com.example.demo.repository.UserJdbcApiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final DataSource dataSource;
    private final UserJdbcApiRepository userJdbcApiRepository;
    private final MessageJdbcApiRepository messageJdbcApiRepository;

    public UserResponseDto findById(Integer id) throws SQLException {
        User retrievedUser = userJdbcApiRepository.findById(id);
        List<Message> retrievedMessages = messageJdbcApiRepository.findByUserId(id);
        return UserResponseDto.from(retrievedUser, retrievedMessages);
    }

    public UserResponseDto create(UserCreateRequestDto request) throws SQLException {
        // (1) Connection 획득한 곳에서
        Connection connection = dataSource.getConnection();

        User createdUser = userJdbcApiRepository.create(connection, request.getName(), request.getAge(), request.getJob(), request.getSpecialty());
        Message createdMessages = messageJdbcApiRepository.create(connection, createdUser.getId(), createdUser.getName() + "님 회원가입 감사드립니다!");
        UserResponseDto response = UserResponseDto.from(createdUser, Collections.singletonList(createdMessages));

        // (2) Connection 반환을 해주어야한다 = 쿼리를 수행하는 메서드 내부에서 Connection 반환을 하면 다른 쿼리의 Connection 을 방해하는것
        connection.close();
        return response;
    }
}
