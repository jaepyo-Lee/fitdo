package com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa;


import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.DailyExerciseRecordJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface DailyExerciseRecordJpaRepository extends JpaRepository<DailyExerciseRecordJpaEntity, Long> {
    @Modifying
    @Query("DELETE FROM DailyExerciseRecordJpaEntity DER " +
            "WHERE DER.dailyRecord.date = :date " +
            "AND DER.dailyRecord.user.id = :userId")
    void deleteAllOfUserExerciseRecordsOnDate(@Param("userId") Long userId,
                                              @Param("date") LocalDate date);
}
