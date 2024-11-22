package com.example.demo.service;

import com.example.demo.controller.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceProxy implements IUserService {
    private final IUserService userService;
    private final PlatformTransactionManager transactionManager;

    public UserResponseDto findById(Integer id) {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            UserResponseDto result = userService.findById(id);
            transactionManager.commit(status);          // (A) Commit - 트랜잭션 추상화
            return result;
        } catch (Exception e) {
            transactionManager.rollback(status);        // (B) Rollback - 트랜잭션 추상화
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "[UserServiceProxy] 트랜잭션 수행 시 실패");
        }
    }

    @Override
    public List<UserResponseDto> findAll() {
        return userService.findAll();
    }

    public UserResponseDto save(String name, Integer age, String job, String specialty) {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            UserResponseDto result = userService.save(name, age, job, specialty);
            transactionManager.commit(status);          // (A) Commit - 트랜잭션 추상화
            return result;
        } catch (Exception e) {
            transactionManager.rollback(status);        // (B) Rollback - 트랜잭션 추상화
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "[UserServiceProxy] 트랜잭션 수행 시 실패");
        }
    }

    @Override
    public UserResponseDto update(Integer id, String name, Integer age, String job, String specialty) {
        return userService.update(id, name, age, job, specialty);
    }

    @Override
    public void delete(Integer id) {
        userService.delete(id);
    }
}
