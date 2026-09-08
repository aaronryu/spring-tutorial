package com.example.demo.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Repository
@RequiredArgsConstructor
public class UserJdbcTemplateRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserJdbc findById(Integer id) {
        int parameter = id;
        UserJdbc retrieved = jdbcTemplate.queryForObject(
                "SELECT * FROM \"user\" WHERE id = ?",
                (resultSet, rowNum) -> {
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
                },
                parameter
        );
        return retrieved;
    }

    public UserJdbc create(String name, Integer age, String job, String specialty) {
        Object[] paramters = new Object[] {
                name,
                age,
                job,
                specialty,
                LocalDateTime.now()
        };
        jdbcTemplate.update(
                "INSERT INTO \"user\" (name, age, job, specialty, created_at) VALUES (?, ?, ?, ?, ?);",
                paramters
        );
        Integer createdUserId = jdbcTemplate.queryForObject(
                "SELECT lastval();",
                Integer.class
        );
        Integer parameter = createdUserId;
        UserJdbc created = jdbcTemplate.queryForObject(
                "SELECT * FROM \"user\" WHERE id = ?;",
                (resultSet, rowNum) -> {
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
                },
                parameter
        );
        return created;
    }
}
