package com.jaejoo.fitdomysql.domain.exercise.infra.repository.impl;

import com.jaejoo.fitdomysql.domain.exercise.infra.repository.ExerciseSetCommandRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.ExerciseSetJpaRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.DailyExerciseJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.ExerciseSetJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ExerciseSetCommandJpaRepositoryImpl implements ExerciseSetCommandRepository {
    private final ExerciseSetJpaRepository jpaRepository;

    @Override
    public List<ExerciseSetJpaEntity> saveAll(List<ExerciseSetJpaEntity> entities) {
        return jpaRepository.saveAll(entities);
    }

    @Override
    public ExerciseSetJpaEntity save(ExerciseSetJpaEntity entity) {
        return jpaRepository.save(entity);
    }

    @Override
    public void deleteAllByDailyExercise(long id) {
        jpaRepository.deleteAllByDailyExercise(id);
    }
}
