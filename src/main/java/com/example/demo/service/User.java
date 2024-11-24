package com.example.demo.service;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@NamedEntityGraph(name = "User.findAllWithMessages", attributeNodes = @NamedAttributeNode("messages"))
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

    @Setter
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Message> messages;

    public static User from(String name, Integer age, String job, String specialty) {
        return new User(null, name, age, job, specialty, LocalDateTime.now(), new ArrayList<>());
    }

    public void addMessage(Message messages) {
        this.messages.add(messages);
    }

    public User updatedFrom(String name, Integer age, String job, String specialty) {
        this.name = name;
        this.age = age;
        this.job = job;
        this.specialty = specialty;
        return this;
    }
}
