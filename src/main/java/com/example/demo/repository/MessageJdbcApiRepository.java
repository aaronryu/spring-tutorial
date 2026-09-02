package com.example.demo.repository;

import lombok.RequiredArgsConstructor;
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
public class MessageJdbcApiRepository {
    private final DataSource dataSource;

    public List<Message> findByUserId(Integer userId) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT * FROM message WHERE user_id = ?");
            statement.setInt(1, userId);
            List<Message> results = new ArrayList<>();
            while (resultSet.next()) {
                results.add(
                        new Message(
                                resultSet.getInt("id"),
                                resultSet.getString("message"),
                                resultSet.getInt("user_id"),
                                resultSet.getTimestamp("created_at")
                                        .toInstant()
                                        .atZone(ZoneId.systemDefault())
                                        .toLocalDateTime()
                        )
                );
            }
            return Collections.emptyList();
        } finally {
            if (null != resultSet) resultSet.close();
            if (null != statement) statement.close();
            if (null != connection) connection.close();
        }
    }

    public Message create(Integer userId, String message) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            // INSERT 유저 정보
            connection = dataSource.getConnection();
            if (userId > 2) throw new RuntimeException("같은 하나의 트랜잭션 내 예외 발생 시 ROLLBACK 되는지 확인하기 위해 일부러 유저 ID 3부터는 메세지 저장을 시도할 시 에러를 발생시킵니다");
            statement = connection.prepareStatement("INSERT INTO message (message, user_id, created_at) VALUES (?, ?, ?);");
            statement.setString(1, message);
            statement.setInt(2, userId);
            statement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            statement.executeUpdate();
            // SELECT 방금 추가한 유저의 id
            Integer createdMessageId = null;
            statement = connection.prepareStatement("SELECT lastval();");
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                createdMessageId = resultSet.getInt("lastval");
            }
            // SELECT 유저 정보
            statement = connection.prepareStatement("SELECT * FROM message WHERE id = ?;");
            statement.setInt(1, createdMessageId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Message(
                        resultSet.getInt("id"),
                        resultSet.getString("message"),
                        resultSet.getInt("user_id"),
                        resultSet.getTimestamp("created_at")
                                .toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDateTime()
                );
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (null != resultSet) resultSet.close();
            if (null != statement) statement.close();
            if (null != connection) connection.close();
        }
    }
}
