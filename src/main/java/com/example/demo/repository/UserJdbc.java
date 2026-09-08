package com.example.demo.repository;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * 기존에 JDBC 실습 시에는 데이터베이스와 완전히 동일하게 FK 키를 갖지 서로에 대한 엔티티 객체를 갖지 않는다
 * - 부모 엔티티 객체 내 자식 엔티티 객체를 갖거나
 * - 자식 엔티티 객체 내 부모 엔티티 객체를 갖지않는다
 */
@AllArgsConstructor
public class UserJdbc {
    private Integer id;
    private String name;
    private Integer age;
    private String job;
    private String specialty;
    private LocalDateTime createdAt;
}
