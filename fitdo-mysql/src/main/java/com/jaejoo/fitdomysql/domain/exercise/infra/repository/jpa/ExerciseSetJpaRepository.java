package com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa;

import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.DailyExerciseJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.ExerciseSetJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseSetJpaRepository extends JpaRepository<ExerciseSetJpaEntity,Long> {

    @Modifying
    @Query("DELETE FROM ExerciseSetJpaEntity es WHERE es.dailyExercise.id = :dailyExerciseId")
    void deleteAllByDailyExercise(@Param("dailyExerciseId") long dailyExerciseId);
}
