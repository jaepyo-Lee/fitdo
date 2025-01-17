package com.jaejoo.fitdomysql.domain.exercise.infra.repository.impl;


import com.jaejoo.fitdomysql.domain.exercise.infra.repository.RecordQueryRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.DailyExerciseJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.DailyJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.ExerciseJpaEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

import static com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.QDailyExerciseJpaEntity.dailyExerciseJpaEntity;
import static com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.QDailyJpaEntity.dailyJpaEntity;

@Repository
@RequiredArgsConstructor
public class DailyRecordDslRepositoryImpl implements RecordQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<DailyJpaEntity> findDailyRecordsByUserAndYearMonth(Long userId, YearMonth yearMonth) {
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        return queryFactory.selectFrom(dailyJpaEntity)
                .where(dailyJpaEntity.user.id.eq(userId),
                        dailyJpaEntity.date.between(startDate, endDate))
                .fetch();
    }

    @Override
    public List<DailyExerciseJpaEntity> findExercisesByDailyRecord(DailyJpaEntity dailyRecord) {
        return queryFactory.selectFrom(dailyExerciseJpaEntity)
                .where(dailyExerciseJpaEntity.daily.eq(dailyRecord))
                .fetch();
    }

    @Override
    public List<DailyExerciseJpaEntity> findExerciseRecordsInDailyRecordDividedBy(ExerciseJpaEntity exercise, DailyJpaEntity dailyRecord) {
        return queryFactory.selectFrom(dailyExerciseJpaEntity)
                .where(dailyExerciseJpaEntity.exercise.eq(exercise))
                .where(dailyExerciseJpaEntity.daily.eq(dailyRecord))
                .fetch();
    }

    @Override
    public List<DailyExerciseJpaEntity> findAllByUserIdAndDate(long userId, LocalDate date) {
        return queryFactory.selectFrom(dailyExerciseJpaEntity)
                .where(dailyExerciseJpaEntity.exercise.user.id.eq(userId))
                .where(dailyExerciseJpaEntity.daily.date.eq(LocalDate.from(date)))
                .fetch();
    }
}
