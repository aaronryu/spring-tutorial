package com.example.demo.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class MessageJdbcApiRepository {
    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    public List<Message> findByUserId(Integer userId) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
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
}
