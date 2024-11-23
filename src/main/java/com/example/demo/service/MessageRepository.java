package com.example.demo.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MessageRepository {
    private final EntityManagerFactory entityManagerFactory;

    public List<Message> findByUserId(int userId) {
        // 1) EntityManagerFactory : 요청마다 EntityManager 생성
        // 2) EntityManager : 요청에 따른 EntityManager 생성 완료
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        // 3) EntityTransaction : 작업 수행을 위한 트랜잭션 설정 및 쿼리 수행
        EntityTransaction transaction = entityManager.getTransaction();
        // 3.1) EntityTransaction 트랜잭션 시작
        transaction.begin();
        try {
            String jpql = "SELECT message From Message message where message.userId = :userId";
            TypedQuery<Message> typedQuery = entityManager.createQuery(jpql, Message.class);
            typedQuery.setParameter("userId", userId);
            List<Message> messages = typedQuery.getResultList();
            // 3.2) EntityTransaction 트랜잭션 커밋(Commit)
            transaction.commit();
            return messages;
        } catch (Exception e) {
            // 3.3) EntityTransaction 트랜잭션 실패(Rollback)
            transaction.rollback();
            log.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "자원에 대한 접근에 문제가 있습니다.");
        } finally {
            // 2.1) EntityManager 종료 (Close)
            entityManager.close();
            // 1.1) EntityManagerFactory 종료 (Close)
//          entityManagerFactory.close();
        }
    }

    public Message save(Message entity) {
        if (true) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "트랜잭션 롤백 여부를 확인하기 위한 의도된 예외");
        }
        // 1) EntityManagerFactory : 요청마다 EntityManager 생성
        // 2) EntityManager : 요청에 따른 EntityManager 생성 완료
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        // 3) EntityTransaction : 작업 수행을 위한 트랜잭션 설정 및 쿼리 수행
        EntityTransaction transaction = entityManager.getTransaction();
        // 3.1) EntityTransaction 트랜잭션 시작
        transaction.begin();
        try {
            // INSERT
            entityManager.persist(entity); // 영구저장
            entityManager.flush();
            Integer createdMessageId = entity.getId();
            // SELECT
            String jpql = "SELECT message From Message message where message.id = :id";
            TypedQuery<Message> typedQuery = entityManager.createQuery(jpql, Message.class);
            typedQuery.setParameter("id", createdMessageId);
            Message message = typedQuery.getSingleResult();
            // 3.2) EntityTransaction 트랜잭션 커밋(Commit)
            transaction.commit();
            return message;
        } catch (Exception e) {
            // 3.3) EntityTransaction 트랜잭션 실패(Rollback)
            transaction.rollback();
            log.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "유저 저장에 실패했습니다.");
        } finally {
            // 2.1) EntityManager 종료 (Close)
            entityManager.close();
            // 1.1) EntityManagerFactory 종료 (Close)
//          entityManagerFactory.close();
        }
    }
}
