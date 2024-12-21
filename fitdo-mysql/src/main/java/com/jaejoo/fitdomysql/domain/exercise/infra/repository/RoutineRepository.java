package com.jaejoo.fitdomysql.domain.exercise.infra.repository;

import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.RoutineJpaEntity;

import java.util.List;

public interface RoutineRepository {
    RoutineJpaEntity save(RoutineJpaEntity routine);
    List<RoutineJpaEntity> findAllByUserId(Long userId);
    RoutineJpaEntity findById(Long routineId);
    void deleteBy(RoutineJpaEntity routine);
}
