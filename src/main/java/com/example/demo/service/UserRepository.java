package com.example.demo.service;

import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepository implements IRepository<Integer, User> {
    private final EntityManagerFactory entityManagerFactory;
//  SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory(); // @Bean 으로 주입받아도 된다.

    public Optional<User> findById(Integer id) {
        // 1) SessionFactory : 요청마다 Session 생성 <- EntityManagerFactory : 요청마다 EntityManager 생성
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        // 2) Session : 요청에 따른 Session 생성 완료 <- EntityManager : 요청에 따른 EntityManager 생성 완료
        Session session = sessionFactory.openSession();
        // 3) Transaction <- EntityTransaction : 작업 수행을 위한 트랜잭션 설정 및 쿼리 수행
        Transaction transaction = session.getTransaction(); // session.beginTransaction() 은 최초 트랜잭션 선언 시 (이미 SpringProxyHandler 에 의해 생성되어있음)
        // 3.1) Transaction 트랜잭션 시작 <- EntityTransaction
        transaction.begin();
        try {
            String jpql = "SELECT user From User user where user.id = :id";
            TypedQuery<User> typedQuery = session.createQuery(jpql, User.class);
            typedQuery.setParameter("id", id);
            User user = typedQuery.getSingleResult();
            // 3.2) Transaction 트랜잭션 커밋(Commit) <- EntityTransaction
            transaction.commit();
            return Optional.ofNullable(user);
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

    public List<User> findAll() {
        // 1) SessionFactory : 요청마다 Session 생성 <- EntityManagerFactory : 요청마다 EntityManager 생성
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        // 2) Session : 요청에 따른 Session 생성 완료 <- EntityManager : 요청에 따른 EntityManager 생성 완료
        Session session = sessionFactory.openSession();
        // 3) Transaction <- EntityTransaction : 작업 수행을 위한 트랜잭션 설정 및 쿼리 수행
        Transaction transaction = session.getTransaction(); // session.beginTransaction() 은 최초 트랜잭션 선언 시 (이미 SpringProxyHandler 에 의해 생성되어있음)
        // 3.1) Transaction 트랜잭션 시작 <- EntityTransaction
        transaction.begin();
        try {
            String jpql = "SELECT user From User user";
            List<User> users = session.createQuery(jpql, User.class).getResultList();
            transaction.commit();
            return users;
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

    public User save(User entity) {
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
            Integer createdUserId = entity.getId();
            // SELECT
            String jpql = "SELECT user From User user where user.id = :id";
            TypedQuery<User> typedQuery = session.createQuery(jpql, User.class);
            typedQuery.setParameter("id", createdUserId);
            User user = typedQuery.getSingleResult();
            // 3.2) Transaction 트랜잭션 커밋(Commit) <- EntityTransaction
            transaction.commit();
            return user;
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

    public void deleteById(Integer id) {
        // 1) SessionFactory : 요청마다 Session 생성 <- EntityManagerFactory : 요청마다 EntityManager 생성
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        // 2) Session : 요청에 따른 Session 생성 완료 <- EntityManager : 요청에 따른 EntityManager 생성 완료
        Session session = sessionFactory.openSession();
        // 3) Transaction <- EntityTransaction : 작업 수행을 위한 트랜잭션 설정 및 쿼리 수행
        Transaction transaction = session.getTransaction(); // session.beginTransaction() 은 최초 트랜잭션 선언 시 (이미 SpringProxyHandler 에 의해 생성되어있음)
        // 3.1) Transaction 트랜잭션 시작 <- EntityTransaction
        transaction.begin();
        try {
            // DELETE
            String jpql = "DELETE From User where id = :id";
            TypedQuery<User> typedQuery = session.createQuery(jpql, User.class);
            typedQuery.setParameter("id", id);
            typedQuery.executeUpdate();
            // 3.2) Transaction 트랜잭션 커밋(Commit) <- EntityTransaction
            transaction.commit();
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
