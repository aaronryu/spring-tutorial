package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.server.ResponseStatusException;

import org.aopalliance.intercept.MethodInterceptor;
import java.lang.reflect.Method;

@Slf4j
@Component
@RequiredArgsConstructor
public class SpringProxyHandler implements MethodInterceptor {
//  private final Object target;    // 이제 Spring 의 ProxyFactoryBean 사용 시 객체 주입이 필요없다.
    private final PlatformTransactionManager transactionManager;

//  invoke 메서드의 파라미터인 MethodInvocation invocation 가 타겟 객체와 메서드 정보들을 모두 다 갖고있기때문이다.
    public Object invoke(MethodInvocation invocation) throws Throwable {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            Object result = invocation.proceed();
            transactionManager.commit(status);          // (A) Commit - 트랜잭션 추상화
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            transactionManager.rollback(status);        // (B) Rollback - 트랜잭션 추상화
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "[SpringProxyHandler] 트랜잭션 수행 시 실패");
        }
    }
}
