package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.ZoneId;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserJdbcTemplateDao {
    private final JdbcTemplate jdbcTemplate;

    public User findById(int userId) {
        String getUserQuery = "SELECT * FROM \"user\" WHERE id = ?";
        int getUserParams = userId;
        return this.jdbcTemplate.queryForObject(
                getUserQuery,
                (resultSet, rowNum) -> new User(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("age"),
                        resultSet.getString("job"),
                        resultSet.getString("specialty"),
                        resultSet.getTimestamp("created_at")
                                .toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDateTime()
                ),
                getUserParams
        );
    }

    public List<User> findAll() {
        String getUserQuery = "SELECT * FROM \"user\"";
        return this.jdbcTemplate.queryForStream(
                getUserQuery,
                (resultSet, rowNum) -> new User(
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
        ).toList();
    }
}
