package com.example.demo.service;

import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public Optional<User> findById(Integer id) {
        // 1) EntityManagerFactory : 요청마다 EntityManager 생성
        // 2) EntityManager : 요청에 따른 EntityManager 생성 완료
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        // 3) EntityTransaction : 작업 수행을 위한 트랜잭션 설정 및 쿼리 수행
        EntityTransaction transaction = entityManager.getTransaction();
        // 3.1) EntityTransaction 트랜잭션 시작
        transaction.begin();
        try {
            String jpql = "SELECT user From User user where user.id = :id";
            TypedQuery<User> typedQuery = entityManager.createQuery(jpql, User.class);
            typedQuery.setParameter("id", id);
            User user = typedQuery.getSingleResult();
            // 3.2) EntityTransaction 트랜잭션 커밋(Commit)
            transaction.commit();
            return Optional.ofNullable(user);
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

    public List<User> findAll() {
        // 1) EntityManagerFactory : 요청마다 EntityManager 생성
        // 2) EntityManager : 요청에 따른 EntityManager 생성 완료
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        // 3) EntityTransaction : 작업 수행을 위한 트랜잭션 설정 및 쿼리 수행
        EntityTransaction transaction = entityManager.getTransaction();
        // 3.1) EntityTransaction 트랜잭션 시작
        transaction.begin();
        try {
            String jpql = "SELECT user From User user";
            List<User> users = entityManager.createQuery(jpql, User.class).getResultList();
            transaction.commit();
            return users;
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

    public User save(User entity) {
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
            Integer createdUserId = entity.getId();
            // SELECT
            String jpql = "SELECT user From User user where user.id = :id";
            TypedQuery<User> typedQuery = entityManager.createQuery(jpql, User.class);
            typedQuery.setParameter("id", createdUserId);
            User user = typedQuery.getSingleResult();
            // 3.2) EntityTransaction 트랜잭션 커밋(Commit)
            transaction.commit();
            return user;
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

    public void deleteById(Integer id) {
        // 1) EntityManagerFactory : 요청마다 EntityManager 생성
        // 2) EntityManager : 요청에 따른 EntityManager 생성 완료
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        // 3) EntityTransaction : 작업 수행을 위한 트랜잭션 설정 및 쿼리 수행
        EntityTransaction transaction = entityManager.getTransaction();
        // 3.1) EntityTransaction 트랜잭션 시작
        transaction.begin();
        try {
            // DELETE
            String jpql = "DELETE From User where id = :id";
            TypedQuery<User> typedQuery = entityManager.createQuery(jpql, User.class);
            typedQuery.setParameter("id", id);
            typedQuery.executeUpdate();
            // 3.2) EntityTransaction 트랜잭션 커밋(Commit)
            transaction.commit();
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
