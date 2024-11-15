package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserJdbcApiDao {
    private final DataSource dataSource;

    public User findById(int userId) throws SQLException {
        Connection connection = null;           // 1
        PreparedStatement statement = null;     // 2
        ResultSet resultSet = null;             // 3
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT * FROM \"user\" WHERE id = ?");
            statement.setInt(1, userId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("age"),
                        resultSet.getString("job"),
                        resultSet.getString("specialty"),
                        resultSet.getTimestamp("created_at")
                                .toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDateTime()
                );
            }
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "유저 정보가 존재하지 않습니다 - id : " + userId);
        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "자원에 대한 접근에 문제가 있습니다.");
        } finally {
            // 자원반납
            if (resultSet != null) resultSet.close();   // 1
            if (statement != null) statement.close();   // 2
            if (connection != null) connection.close(); // 3
        }
    }

    public List<User> findAll() throws SQLException {
        Connection connection = null;           // 1
        PreparedStatement statement = null;     // 2
        ResultSet resultSet = null;             // 3
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT * FROM \"user\"");
            resultSet = statement.executeQuery();
            List<User> results = new ArrayList();
            while (resultSet.next()) {
                results.add(
                        new User(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getInt("age"),
                                resultSet.getString("job"),
                                resultSet.getString("specialty"),
                                resultSet.getTimestamp("created_at")
                                        .toInstant()
                                        .atZone(ZoneId.systemDefault())
                                        .toLocalDateTime()
                        )
                );
            }
            return results;
        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "자원에 대한 접근에 문제가 있습니다.");
        } finally {
            // 자원반납
            if (resultSet != null) resultSet.close();   // 1
            if (statement != null) statement.close();   // 2
            if (connection != null) connection.close(); // 3
        }
    }
}
