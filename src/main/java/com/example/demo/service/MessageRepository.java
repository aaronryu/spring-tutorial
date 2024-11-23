package com.example.demo.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MessageRepository {
    private final EntityManagerFactory entityManagerFactory;
//  SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory(); // @Bean 으로 주입받아도 된다.

    public List<Message> findByUserId(int userId) {
        // 1) SessionFactory : 요청마다 Session 생성 <- EntityManagerFactory : 요청마다 EntityManager 생성
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        // 2) Session : 요청에 따른 Session 생성 완료 <- EntityManager : 요청에 따른 EntityManager 생성 완료
        Session session = sessionFactory.openSession();
        // 3) Transaction <- EntityTransaction : 작업 수행을 위한 트랜잭션 설정 및 쿼리 수행
        Transaction transaction = session.getTransaction(); // session.beginTransaction() 은 최초 트랜잭션 선언 시 (이미 SpringProxyHandler 에 의해 생성되어있음)
        // 3.1) Transaction 트랜잭션 시작 <- EntityTransaction
        transaction.begin();
        try {
            String jpql = "SELECT message From Message message where message.userId = :userId";
            TypedQuery<Message> typedQuery = session.createQuery(jpql, Message.class);
            typedQuery.setParameter("userId", userId);
            List<Message> messages = typedQuery.getResultList();
            // 3.2) Transaction 트랜잭션 커밋(Commit) <- EntityTransaction
            transaction.commit();
            return messages;
        } catch (Exception e) {
            // 3.3) Transaction 트랜잭션 실패(Rollback) <- EntityTransaction
            transaction.rollback();
            log.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "자원에 대한 접근에 문제가 있습니다.");
        } finally {
            // 2.1) Session 종료 (Close) <- EntityManager
            session.close();
            // 1.1) SessionFactory 종료 (Close) <- EntityManagerFactory
//          entityManagerFactory.close();
        }
    }

    public Message save(Message entity) {
        if (true) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "트랜잭션 롤백 여부를 확인하기 위한 의도된 예외");
        }
        // 1) SessionFactory : 요청마다 Session 생성 <- EntityManagerFactory : 요청마다 EntityManager 생성
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        // 2) Session : 요청에 따른 Session 생성 완료 <- EntityManager : 요청에 따른 EntityManager 생성 완료
        Session session = sessionFactory.openSession();
        // 3) Transaction <- EntityTransaction : 작업 수행을 위한 트랜잭션 설정 및 쿼리 수행
        Transaction transaction = session.getTransaction(); // session.beginTransaction() 은 최초 트랜잭션 선언 시 (이미 SpringProxyHandler 에 의해 생성되어있음)
        // 3.1) Transaction 트랜잭션 시작 <- EntityTransaction
        transaction.begin();
        try {
            // INSERT
            session.persist(entity); // 영구저장
            session.flush();
            Integer createdMessageId = entity.getId();
            // SELECT
            String jpql = "SELECT message From Message message where message.id = :id";
            TypedQuery<Message> typedQuery = session.createQuery(jpql, Message.class);
            typedQuery.setParameter("id", createdMessageId);
            Message message = typedQuery.getSingleResult();
            // 3.2) Transaction 트랜잭션 커밋(Commit) <- EntityTransaction
            transaction.commit();
            return message;
        } catch (Exception e) {
            // 3.3) Transaction 트랜잭션 실패(Rollback) <- EntityTransaction
            transaction.rollback();
            log.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "자원에 대한 접근에 문제가 있습니다.");
        } finally {
            // 2.1) Session 종료 (Close) <- EntityManager
            session.close();
            // 1.1) SessionFactory 종료 (Close) <- EntityManagerFactory
//          entityManagerFactory.close();
        }
    }
}
