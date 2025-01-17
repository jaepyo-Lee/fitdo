package com.jaejoo.fitdomysql.domain.exercise.infra.repository;

import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.DailyExerciseJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.ExerciseSetJpaEntity;

import java.util.List;

public interface ExerciseSetCommandRepository {
    ExerciseSetJpaEntity save(ExerciseSetJpaEntity entity);

    List<ExerciseSetJpaEntity> saveAll(List<ExerciseSetJpaEntity> entities);

    void deleteAllByDailyExercise(long id);


}
