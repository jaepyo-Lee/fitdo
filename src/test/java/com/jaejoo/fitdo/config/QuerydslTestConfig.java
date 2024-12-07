package com.jaejoo.fitdo.config;

import com.jaejoo.fitdo.domain.exercise.infra.repository.impl.DailyExerciseRecordDslRepositoryImpl;
import com.jaejoo.fitdo.domain.exercise.infra.repository.impl.DailyRecordDslRepositoryImpl;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class QuerydslTestConfig {
    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public DailyRecordDslRepositoryImpl dailyRecordDslRepositoryImpl() {
        return new DailyRecordDslRepositoryImpl(jpaQueryFactory());
    }

    @Bean
    public DailyExerciseRecordDslRepositoryImpl dailyExerciseRecordDslRepository(){
        return new DailyExerciseRecordDslRepositoryImpl(jpaQueryFactory());
    }
}
