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

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepository implements IRepository<Integer, User> {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Optional<User> findById(Integer id) {
        String jpql = "SELECT user From User user where user.id = :id";
        TypedQuery<User> typedQuery = entityManager.createQuery(jpql, User.class);
        typedQuery.setParameter("id", id);
        User user = typedQuery.getSingleResult();
        return Optional.ofNullable(user);
    }

    @Transactional
    public List<User> findAll() {
        String jpql = "SELECT user From User user";
        List<User> users = entityManager.createQuery(jpql, User.class).getResultList();
        return users;
    }

    @Transactional
    public User save(User entity) {
        // INSERT
        entityManager.persist(entity); // 영구저장
        entityManager.flush();
        Integer createdUserId = entity.getId();
        // SELECT
        String jpql = "SELECT user From User user where user.id = :id";
        TypedQuery<User> typedQuery = entityManager.createQuery(jpql, User.class);
        typedQuery.setParameter("id", createdUserId);
        User user = typedQuery.getSingleResult();
        return user;
    }

    @Transactional
    public void deleteById(Integer id) {
        String jpql = "DELETE From User where id = :id";
        TypedQuery<User> typedQuery = entityManager.createQuery(jpql, User.class);
        typedQuery.setParameter("id", id);
        typedQuery.executeUpdate();
    }
}
