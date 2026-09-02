package com.example.demo.service;

import com.example.demo.controller.dto.UserCreateRequestDto;
import com.example.demo.controller.dto.UserResponseDto;
import com.example.demo.repository.Message;
import com.example.demo.repository.MessageJdbcApiRepository;
import com.example.demo.repository.User;
import com.example.demo.repository.UserJdbcApiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final DataSource dataSource;
//  private final PlatformTransactionManager platformTransactionManager;
    private final UserJdbcApiRepository userJdbcApiRepository;
    private final MessageJdbcApiRepository messageJdbcApiRepository;

    public UserResponseDto findById(Integer id) throws SQLException {
        User retrievedUser = userJdbcApiRepository.findById(id);
        List<Message> retrievedMessages = messageJdbcApiRepository.findByUserId(id);
        return UserResponseDto.from(retrievedUser, retrievedMessages);
    }

    public UserResponseDto create(UserCreateRequestDto request) throws SQLException {
        PlatformTransactionManager platformTransactionManager = new DataSourceTransactionManager(dataSource);
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            User createdUser = userJdbcApiRepository.create(request.getName(), request.getAge(), request.getJob(), request.getSpecialty());
            Message createdMessages = messageJdbcApiRepository.create(createdUser.getId(), createdUser.getName() + "님 회원가입 감사드립니다!");
            UserResponseDto response = UserResponseDto.from(createdUser, Collections.singletonList(createdMessages));
            platformTransactionManager.commit(transactionStatus); // * 자동 커밋이 꺼져있기때문에(OFF) 임시 저장소에 쌓여있는 그동안의 쿼리 결과들을 수동 커밋 COMMIT 통해 단 한방에 데이터베이스에 그 모든것들을 최종 반영해야한다
            return response;
        } catch (Exception e) {
            platformTransactionManager.rollback(transactionStatus); // * 자동 커밋이 꺼져있기때문에(OFF) 임시 저장소에 쌓여있는 그동안의 쿼리 결과들을 ROLLBACK 통해 단 한방에 날려버릴 수 있다
            throw e;
        }
    }
}
