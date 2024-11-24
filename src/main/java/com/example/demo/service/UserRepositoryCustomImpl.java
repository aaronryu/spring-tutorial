package com.example.demo.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<User> findByName(String name) {
        return jpaQueryFactory.selectFrom(QUser.user)
                .where(QUser.user.name.trim().eq(name))
                .fetch();
    }
}
