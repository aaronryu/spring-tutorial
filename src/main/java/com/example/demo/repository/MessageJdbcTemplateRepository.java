package com.example.demo.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MessageJdbcTemplateRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<Message> findByUserId(Integer userId) {
        Integer parameter = userId;
        List<Message> messages = jdbcTemplate.queryForStream(
                "SELECT * FROM message WHERE user_id = ?",
                (resultSet, rowNum) -> {
                    return new Message(
                            resultSet.getInt("id"),
                            resultSet.getString("message"),
                            resultSet.getInt("user_id"),
                            resultSet.getTimestamp("created_at")
                                    .toInstant()
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDateTime()
                    );
                },
                parameter
        ).toList();
        return messages;
    }

    public Message create(Integer userId, String message) {
        if (userId > 2) throw new RuntimeException("같은 하나의 트랜잭션 내 예외 발생 시 ROLLBACK 되는지 확인하기 위해 일부러 유저 ID 3부터는 메세지 저장을 시도할 시 에러를 발생시킵니다");
        Object[] parameters = new Object[] {
                message,
                userId,
                LocalDateTime.now()
        };
        jdbcTemplate.update(
                "INSERT INTO message (message, user_id, created_at) VALUES (?, ?, ?);",
                parameters
        );
        Integer createdMessageId = jdbcTemplate.queryForObject(
                "SELECT lastval();",
                Integer.class
        );
        Integer parameter = createdMessageId;
        Message created = jdbcTemplate.queryForObject(
                "SELECT * FROM message WHERE id = ?;",
                (resultSet, rowNum) -> {
                    return new Message(
                            resultSet.getInt("id"),
                            resultSet.getString("message"),
                            resultSet.getInt("user_id"),
                            resultSet.getTimestamp("created_at")
                                    .toInstant()
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDateTime()
                    );
                },
                parameter
        );
        return created;
    }
}
