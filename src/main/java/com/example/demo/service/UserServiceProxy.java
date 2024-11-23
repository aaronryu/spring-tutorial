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

// @Service
/**
 * CGLIB 사용을 위해 UserService 는 이제 더 이상 IUserService 를 사용하지 않기때문에
 * IUserService 의 유일한 구현체인 UserServiceProxy 가 안에서 IUserService 구현체 Bean 을 찾을때 자기 자신을 참조하는
 * Circular References 가 발생하니 IUserService Bean 빈 생성하지 않도록 @Service 를 코멘트 처리
 */
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
