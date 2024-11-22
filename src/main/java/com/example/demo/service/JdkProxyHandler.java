package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@Slf4j
@RequiredArgsConstructor
public class JdkProxyHandler implements InvocationHandler {
    private final Object target;    // 어떤 객체든 들어올 수 있기때문에 Object 이라는 공통 타입(클래스)으로 설정하였다.
    private final PlatformTransactionManager transactionManager;

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            Object result = method.invoke(target, args);
            transactionManager.commit(status);          // (A) Commit - 트랜잭션 추상화
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            transactionManager.rollback(status);        // (B) Rollback - 트랜잭션 추상화
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "[JdkProxyHandler] 트랜잭션 수행 시 실패");
        }
    }
}
