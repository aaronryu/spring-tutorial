package com.example.demo.transaction;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Aspect
@Component
@RequiredArgsConstructor
public class TransactionAspect {
    private final PlatformTransactionManager platformTransactionManager;

    @Around("@annotation(annotation)")
    public Object transaction(ProceedingJoinPoint joinPoint, CustomTransaction annotation) throws Throwable {
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setPropagationBehavior(annotation.propagation().value());
        transactionDefinition.setIsolationLevel(annotation.isolation().value());
        transactionDefinition.setTimeout(annotation.timeout());
        transactionDefinition.setReadOnly(annotation.readOnly());
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(transactionDefinition);
        try {
            Object result = joinPoint.proceed();
            platformTransactionManager.commit(transactionStatus);
            return result;
        } catch (Throwable e) {
            platformTransactionManager.rollback(transactionStatus);
            throw e;
        }
    }
}
