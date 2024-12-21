package com.jaejoo.fitdomysql.domain.exercise.infra.repository.impl;


import com.jaejoo.fitdomysql.domain.exercise.infra.repository.RecordQueryRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.DailyExerciseRecordJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.DailyRecordJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.ExerciseJpaEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import static com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.QDailyExerciseRecordJpaEntity.dailyExerciseRecordJpaEntity;
import static com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.QDailyRecordJpaEntity.dailyRecordJpaEntity;

@Repository
@RequiredArgsConstructor
public class DailyRecordDslRepositoryImpl implements RecordQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<DailyRecordJpaEntity> findDailyRecordsByUserAndYearMonth(Long userId, YearMonth yearMonth) {
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        return queryFactory.selectFrom(dailyRecordJpaEntity)
                .where(dailyRecordJpaEntity.user.id.eq(userId),
                        dailyRecordJpaEntity.date.between(startDate, endDate))
                .fetch();
    }

    @Override
    public List<ExerciseJpaEntity> findExercisesByDailyRecord(DailyRecordJpaEntity dailyRecord) {
        return queryFactory.select(dailyExerciseRecordJpaEntity.exercise)
                .from(dailyExerciseRecordJpaEntity)
                .where(dailyExerciseRecordJpaEntity.dailyRecord.eq(dailyRecord))
                .groupBy(dailyExerciseRecordJpaEntity.exercise)
                .fetch();
    }

    @Override
    public List<DailyExerciseRecordJpaEntity> findExerciseRecordsInDailyRecordDividedBy(ExerciseJpaEntity exercise, DailyRecordJpaEntity dailyRecord) {
        return queryFactory.selectFrom(dailyExerciseRecordJpaEntity)
                .where(dailyExerciseRecordJpaEntity.exercise.eq(exercise))
                .where(dailyExerciseRecordJpaEntity.dailyRecord.eq(dailyRecord))
                .fetch();
    }
}
