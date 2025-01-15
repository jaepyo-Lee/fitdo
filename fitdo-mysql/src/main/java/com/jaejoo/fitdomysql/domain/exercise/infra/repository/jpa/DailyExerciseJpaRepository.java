package com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa;


import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.DailyExerciseJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface DailyExerciseJpaRepository extends JpaRepository<DailyExerciseJpaEntity, Long> {
    @Modifying
    @Query("DELETE FROM DailyExerciseJpaEntity de WHERE de.daily.user.id = :userId AND de.daily.date = :date")
    void deleteAllOfUserExerciseRecordsOnDate(@Param("userId") Long userId,
                                              @Param("date") LocalDate date);
}
