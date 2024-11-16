package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MessageJdbcApiDao {
    private final DataSource dataSource;

    public List<Message> findByUserId(int userId) throws SQLException {
        Connection connection = null;           // 1
        PreparedStatement statement = null;     // 2
        ResultSet resultSet = null;             // 3
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT * FROM \"message\" WHERE user_id = ?");
            statement.setInt(1, userId);
            resultSet = statement.executeQuery();
            List<Message> results = new ArrayList();
            while (resultSet.next()) {
                results.add(
                        new Message(
                                resultSet.getInt("id"),
                                resultSet.getInt("user_id"),
                                resultSet.getString("message"),
                                resultSet.getTimestamp("created_at")
                                        .toInstant()
                                        .atZone(ZoneId.systemDefault())
                                        .toLocalDateTime()
                        )
                );
            }
            return results;
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "자원에 대한 접근에 문제가 있습니다.");
        } finally {
            // 자원반납
            if (resultSet != null) resultSet.close();   // 1
            if (statement != null) statement.close();   // 2
            if (connection != null) connection.close(); // 3
        }
    }

    public List<Message> save(Integer userId, String message) throws SQLException {
        Connection connection = null;           // 1
        PreparedStatement statement = null;     // 2
        ResultSet resultSet = null;             // 3
        try {
            connection = dataSource.getConnection();    // 1
            // INSERT MESSAGE
            statement = connection.prepareStatement(    // (A)-2:Statement
                    "INSERT INTO \"message\" (user_id, message, created_at) VALUES (?, ?, ?)"
            );
            statement.setInt(1, userId);
            statement.setString(2, message);
            statement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            int executedNumberOfQuery = statement.executeUpdate();
            // SELECT MESSAGE
            statement = connection.prepareStatement("SELECT * FROM \"message\" WHERE user_id = ?");
            statement.setInt(1, userId);
            resultSet = statement.executeQuery();
            List<Message> results = new ArrayList();
            while (resultSet.next()) {
                results.add(
                        new Message(
                                resultSet.getInt("id"),
                                resultSet.getInt("user_id"),
                                resultSet.getString("message"),
                                resultSet.getTimestamp("created_at")
                                        .toInstant()
                                        .atZone(ZoneId.systemDefault())
                                        .toLocalDateTime()
                        )
                );
            }
            return results;
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    String.format("메세지가 저장되지 않았거나 자원에 대한 접근에 문제가 있습니다. - userId : %s, message : %s", userId, message)
            );
        } finally {
            // 자원반납
            if (resultSet != null) resultSet.close();   // 1
            if (statement != null) statement.close();   // 2
            if (connection != null) connection.close(); // 3
        }
    }
}
