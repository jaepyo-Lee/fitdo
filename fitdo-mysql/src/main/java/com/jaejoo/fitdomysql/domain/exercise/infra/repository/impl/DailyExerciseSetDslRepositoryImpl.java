package com.jaejoo.fitdomysql.domain.exercise.infra.repository.impl;


import com.jaejoo.fitdomysql.domain.exercise.infra.repository.ExerciseSetQueryRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.impl.dto.ProgressInDateDto;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.DailyExerciseJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.ExerciseSetJpaEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import static com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.QDailyExerciseJpaEntity.dailyExerciseJpaEntity;
import static com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.QDailyJpaEntity.dailyJpaEntity;
import static com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.QExerciseJpaEntity.exerciseJpaEntity;
import static com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.QExerciseSetJpaEntity.exerciseSetJpaEntity;

@Repository
@RequiredArgsConstructor
public class DailyExerciseSetDslRepositoryImpl implements ExerciseSetQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<ProgressInDateDto> findAllProgress(Long userId, YearMonth yearMonth) {
        LocalDate endDate = yearMonth.atEndOfMonth();
        LocalDate startDate = yearMonth.atDay(1);
        return queryFactory.select(Projections.constructor(ProgressInDateDto.class,
                        dailyJpaEntity.date,
                        exerciseSetJpaEntity.done))
                .from(dailyJpaEntity)
                .join(dailyExerciseJpaEntity)
                .on(dailyExerciseJpaEntity.daily.eq(dailyJpaEntity))
                .join(exerciseSetJpaEntity)
                .on(exerciseSetJpaEntity.dailyExercise.eq(dailyExerciseJpaEntity))
                .where(dailyJpaEntity.user.id.eq(userId), dailyJpaEntity.date.between(startDate, endDate))
                .fetch();
    }

    @Override
    public List<ExerciseSetJpaEntity> findExerciseRecordAtDate(Long userId, LocalDate date) {
        List<ExerciseSetJpaEntity> fetch = queryFactory.select(exerciseSetJpaEntity)
                .from(exerciseSetJpaEntity)
                .join(dailyExerciseJpaEntity)
                .on(exerciseSetJpaEntity.dailyExercise.eq(dailyExerciseJpaEntity))
                .join(dailyJpaEntity)
                .on(dailyExerciseJpaEntity.daily.eq(dailyJpaEntity))
                .where(dailyJpaEntity.date.eq(date))
                .fetch();
        return fetch;
    }

    @Override
    public List<DailyExerciseJpaEntity> findExerciseAndRecord(Long userId, LocalDate date) {
        return queryFactory.select(dailyExerciseJpaEntity)
                .from(dailyExerciseJpaEntity)
                .join(exerciseJpaEntity)
                .on(exerciseJpaEntity.user.id.eq(userId))
                .on(dailyExerciseJpaEntity.exercise.eq(exerciseJpaEntity))
                .join(dailyJpaEntity)
                .on(dailyExerciseJpaEntity.daily.eq(dailyJpaEntity))
                .where(dailyJpaEntity.date.eq(date))
                .fetch();
    }

    @Override
    public List<ExerciseSetJpaEntity> findAllByDailyExercise(DailyExerciseJpaEntity dailyExercise) {
        return queryFactory.selectFrom(exerciseSetJpaEntity)
                .join(dailyExerciseJpaEntity)
                .on(exerciseSetJpaEntity.dailyExercise.eq(dailyExercise))
                .fetch();
    }
}
