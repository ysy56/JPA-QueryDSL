package com.sparta.greeypeople.common;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing // 시간 자동 변경이 가능하도록 합니다.
public class JpaConfig {

    @PersistenceContext
    private EntityManager entityManager;


    @PersistenceContext
    private EntityManager entityManager2;


    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public JPAQueryFactory jpaQueryFactory2() {
        return new JPAQueryFactory(entityManager2);
    }
}
