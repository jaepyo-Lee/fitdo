package com.jaejoo.fitdo.domain.exercise.infra.repository.impl;

import com.jaejoo.fitdo.domain.exercise.infra.repository.ExerciseRecordQueryRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.impl.dto.ProgressInDateDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import static com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.QDailyExerciseRecordJpaEntity.dailyExerciseRecordJpaEntity;
import static com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.QDailyRecordJpaEntity.dailyRecordJpaEntity;

@Repository
@RequiredArgsConstructor
public class DailyExerciseRecordDslRepositoryImpl implements ExerciseRecordQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<ProgressInDateDto> findAllProgressInMonthOfUser(Long userId, YearMonth yearMonth) {
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
}
