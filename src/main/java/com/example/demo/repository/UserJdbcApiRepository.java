package com.example.demo.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Repository
@RequiredArgsConstructor
public class UserJdbcApiRepository {
    private final DataSource dataSource;

    public UserJdbc findById(Integer id) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DataSourceUtils.getConnection(dataSource); // 트랜잭션 동기화 : 읽기(R)
            statement = connection.prepareStatement("SELECT * FROM \"user\" WHERE id = ?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new UserJdbc(
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
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (null != resultSet) resultSet.close();
            if (null != statement) statement.close();
            if (null != connection) connection.close();
        }
    }

    public UserJdbc create(String name, Integer age, String job, String specialty) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            // INSERT 유저 정보
            connection = DataSourceUtils.getConnection(dataSource); // 트랜잭션 동기화 : 읽기(R)
            statement = connection.prepareStatement("INSERT INTO \"user\" (name, age, job, specialty, created_at) VALUES (?, ?, ?, ?, ?);");
            statement.setString(1, name);
            statement.setInt(2, age);
            statement.setString(3, job);
            statement.setString(4, specialty);
            statement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            statement.executeUpdate();
            // SELECT 방금 추가한 유저의 id
            Integer createdUserId = null;
            statement = connection.prepareStatement("SELECT lastval();");
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                createdUserId = resultSet.getInt("lastval");
            }
            // SELECT 유저 정보
            statement = connection.prepareStatement("SELECT * FROM \"user\" WHERE id = ?;");
            statement.setInt(1, createdUserId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new UserJdbc(
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
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (null != resultSet) resultSet.close();
                if (null != statement) statement.close();
//              if (null != connection) connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
