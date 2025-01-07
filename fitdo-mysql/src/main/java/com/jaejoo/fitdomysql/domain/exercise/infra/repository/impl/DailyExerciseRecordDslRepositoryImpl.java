package com.jaejoo.fitdomysql.domain.exercise.infra.repository.impl;


import com.jaejoo.fitdomysql.domain.exercise.infra.repository.ExerciseRecordQueryRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.impl.dto.ProgressInDateDto;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.DailyExerciseRecordJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.QDailyExerciseRecordJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.QDailyRecordJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.projectiondto.ExerciseAndRecordDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import static com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.QDailyExerciseRecordJpaEntity.dailyExerciseRecordJpaEntity;
import static com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.QDailyRecordJpaEntity.dailyRecordJpaEntity;
import static com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.QExerciseJpaEntity.exerciseJpaEntity;

@Repository
@RequiredArgsConstructor
public class DailyExerciseRecordDslRepositoryImpl implements ExerciseRecordQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<ProgressInDateDto> findAllProgress(Long userId, YearMonth yearMonth) {
        LocalDate endDate = yearMonth.atEndOfMonth();
        LocalDate startDate = yearMonth.atDay(1);

        return queryFactory.select(Projections.constructor(ProgressInDateDto.class,
                        dailyRecordJpaEntity.date,
                        dailyExerciseRecordJpaEntity.isProgress))
                .from(dailyRecordJpaEntity)
                .join(dailyExerciseRecordJpaEntity)
                .on(dailyExerciseRecordJpaEntity.dailyRecord.eq(dailyRecordJpaEntity))
                .where(dailyRecordJpaEntity.user.id.eq(userId), dailyRecordJpaEntity.date.between(startDate, endDate))
                .fetch();

    }

    @Override
    public List<DailyExerciseRecordJpaEntity> findExerciseRecordAtDate(Long userId, LocalDate date) {
        return queryFactory.select(dailyExerciseRecordJpaEntity)
                .from(dailyExerciseRecordJpaEntity)
                .join(dailyRecordJpaEntity)
                .on(dailyExerciseRecordJpaEntity.dailyRecord.id.eq(dailyRecordJpaEntity.id))
                .on(dailyRecordJpaEntity.date.eq(date))
                .on(dailyRecordJpaEntity.user.id.eq(userId))
                .fetch();
    }

    @Override
    public List<ExerciseAndRecordDto> findExerciseAndRecord(Long userId, LocalDate date) {
        return queryFactory.select(Projections.constructor(
                        ExerciseAndRecordDto.class,
                        exerciseJpaEntity,
                        dailyExerciseRecordJpaEntity
                ))
                .from(dailyExerciseRecordJpaEntity)
                .join(dailyExerciseRecordJpaEntity.dailyRecord, dailyRecordJpaEntity)
                .on(dailyRecordJpaEntity.date.eq(date)
                        .and(dailyRecordJpaEntity.user.id.eq(userId)))
                .join(dailyExerciseRecordJpaEntity.exercise, exerciseJpaEntity)
                .fetch();
    }
}
