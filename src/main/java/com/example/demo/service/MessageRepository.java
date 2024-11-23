package com.example.demo.service;

import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MessageRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public List<Message> findByUserId(int userId) {
        String jpql = "SELECT message From Message message where message.userId = :userId";
        TypedQuery<Message> typedQuery = entityManager.createQuery(jpql, Message.class);
        typedQuery.setParameter("userId", userId);
        List<Message> messages = typedQuery.getResultList();
        return messages;
    }

    @Transactional
    public Message save(Message entity) {
        if (true) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "트랜잭션 롤백 여부를 확인하기 위한 의도된 예외");
        }
        // INSERT
        entityManager.persist(entity); // 영구저장
        entityManager.flush();
        Integer createdMessageId = entity.getId();
        // SELECT
        String jpql = "SELECT message From Message message where message.id = :id";
        TypedQuery<Message> typedQuery = entityManager.createQuery(jpql, Message.class);
        typedQuery.setParameter("id", createdMessageId);
        Message message = typedQuery.getSingleResult();
        return message;
    }
}
