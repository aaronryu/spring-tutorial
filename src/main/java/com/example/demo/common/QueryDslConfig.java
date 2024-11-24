package com.example.demo.common;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing // @Entity로 관리되는 JPA 엔터티들의 상태 변화를 감시하는 기능을 킨다는 뜻
public class QueryDslConfig {
    @PersistenceContext
    // ㄴ 따로 옵션이 없다면 Transaction-Scoped, Spring 은 EntityManager 싱글톤을 프록시 객체를 만들어 JPA EntityManager 트랜잭션 단위의 생성 담당
    // 그래서 EntityManager 가 싱글톤인게 이상하겠지만 사실은 Spring EntityManager 이기에 말이 되는것
    private EntityManager entityManager; // EntityManager 는 JPA 에서 엔터티의 생성, 조회, 수정 삭제를 수행하는 객체

    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
        // 쿼리를 작성하는 JPAQueryFactory 에 Spring EntityManager 싱글톤 프록시를 넘겨 사용
        return new JPAQueryFactory(entityManager);
    }
}
