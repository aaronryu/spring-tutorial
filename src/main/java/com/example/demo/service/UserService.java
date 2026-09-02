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
        connection.setAutoCommit(false); // * 중요 : 자동 커밋이 꺼진(OFF) 단 하나의 공용 Connection - 그래야 이 Connection 내부에서 실행되는 모든 쿼리들은 각각 쿼리 실행 시 데이터베이스 내 최종 반영되지 않고 임시 저장소에서 대기할 수 있다
        try {
            User createdUser = userJdbcApiRepository.create(connection, request.getName(), request.getAge(), request.getJob(), request.getSpecialty());
            Message createdMessages = messageJdbcApiRepository.create(connection, createdUser.getId(), createdUser.getName() + "님 회원가입 감사드립니다!");
            UserResponseDto response = UserResponseDto.from(createdUser, Collections.singletonList(createdMessages));
            connection.commit(); // * 자동 커밋이 꺼져있기때문에(OFF) 임시 저장소에 쌓여있는 그동안의 쿼리 결과들을 수동 커밋 COMMIT 통해 단 한방에 데이터베이스에 그 모든것들을 최종 반영해야한다
            return response;
        } catch (Exception e) {
            connection.rollback(); // * 자동 커밋이 꺼져있기때문에(OFF) 임시 저장소에 쌓여있는 그동안의 쿼리 결과들을 ROLLBACK 통해 단 한방에 날려버릴 수 있다
            throw e;
        } finally {
            // (2) Connection 반환을 해주어야한다 = 쿼리를 수행하는 메서드 내부에서 Connection 반환을 하면 다른 쿼리의 Connection 을 방해하는것
            connection.setAutoCommit(true); // * 중요 : DataSource 본질적으로 Connection 재사용이기때문에 우리가 사용할때 어떤 설정을 했다면 그 더러운 상태로 반환하지말고 원상복구(모든 옵션들을 다시 원점복귀)하여 반환해야한다
            connection.close();
        }
    }
}
