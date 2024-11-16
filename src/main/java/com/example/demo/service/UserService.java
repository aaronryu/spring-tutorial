package com.example.demo.service;

import com.example.demo.controller.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserJdbcApiDao userJdbcRepository;
    private final MessageJdbcApiDao messageJdbcRepository;
    private final UserJdbcTemplateDao userJdbcTemplateRepository;

    private final DataSource dataSource;

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
        TransactionSynchronizationManager.initSynchronization();
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try {
//          connection = dataSource.getConnection();    // Connection 생성
            connection.setAutoCommit(false);            // Connection Auto-Commit 옵션 끄기
            User user = userJdbcRepository.save(/* connection, */name, age, job, specialty);
            List<Message> messages = messageJdbcRepository.save(/* connection, */user.getId(), user.getName() + "님 가입을 환영합니다.");
            connection.commit();                        // (A) Commit
            UserResponseDto result = UserResponseDto.from(user);
            result.setMessages(messages);
            return result;
        } catch (SQLException e) {
            try {
                connection.rollback();                  // (B) Rollback
            } catch (final SQLException ignored) {
            }
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "자원 반납 시 문제가 있습니다.");
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    public UserResponseDto update(Integer id, String name, Integer age, String job, String specialty) {
        User user = userJdbcTemplateRepository.update(id, name, age, job, specialty);
        return UserResponseDto.from(user);
    }

    public void delete(Integer id) {
        userJdbcTemplateRepository.delete(id);
    }
}
