package com.example.demo.service;

import lombok.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class User {
    /**
     * JPA 통한 Database 사용 시 @GeneratedValue 전략에 대해 조금 상세히 알 필요가 있다.
     * - AUTO     : ID 생성 책임이 JPA 에게 있다 (JPA 는 hibernate_sequence 라는 sequence 테이블을 만들어 활용, nextval 호출)
     * - IDENTITY : ID 생성 책임을 Database 에게 위임한다. (PostgresQL 은 Primary Key 에 대해 SERIAL 로 정의 및 DB 자체적으로 Sequence 생성)
     * > MySQL 라면 AUTO_INCREMENT 사용할것이고,
     * > PostgresQL 이라면 SERIAL + Sequence 사용 (sequence name 형식은 {tablename}_{columnname}_seq), currval 호출)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Integer id;
    private String name;
    private Integer age;
    private String job;
    private String specialty;
    private LocalDateTime createdAt;
}
