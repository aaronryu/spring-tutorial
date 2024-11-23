package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Method;

@Slf4j
@RequiredArgsConstructor
public class CglibProxyHandler implements MethodInterceptor {
    private final Object target;    // 어떤 객체든 들어올 수 있기때문에 Object 이라는 공통 타입(클래스)으로 설정하였다.
    private final PlatformTransactionManager transactionManager;

    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            Object result = method.invoke(target, args);            // Method     .invoke : 이렇게 Java Reflection API 사용해도 괜찮은데
//          Object result = methodProxy.invoke(target, args);       // MethodProxy.invoke : 공식 설명으로는 이게 위엣 방법보다 좀 더 빠르다고 한다.
//          - 하지만 MethodProxy 를 사용하면 컴파일 시 ClassLoader Mismatch 에러가 발생하게된다.
//          - 이유는 MethodProxy 사용 시 직접 (런타임때) 바이트코드를 조작하여 새로운 클래스를 만들기 위해 Custom ClassLoader 를 사용한다.
//          - Custom ClassLoader 내에서는 CGLIB 에서 조작(생성)한 바이트코드를 원래 ClassLoader 의 defineClass() 로 주입해준다.
//          - 이런 과정에 의해 새로 만들어진 바이트코드(클래스가) Custom ClassLoader 가 아닌 기존 ClassLoader 에 위치하게 해야한다
//          = consider co-locating the affected class in that target ClassLoader instead
//          - JVM should be started with --add-opens=java.base/java.lang=ALL-UNNAMED 옵션을 통해 AppClassLoader 의 defineClass 로 만든 바이트코드가 주입될 수 있게 해야한다.
            transactionManager.commit(status);          // (A) Commit - 트랜잭션 추상화
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            transactionManager.rollback(status);        // (B) Rollback - 트랜잭션 추상화
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "[CglibProxyHandler] 트랜잭션 수행 시 실패");
        }
    }
}
