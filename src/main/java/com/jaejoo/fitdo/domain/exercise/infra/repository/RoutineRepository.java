package com.jaejoo.fitdo.domain.exercise.infra.repository;

import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.RoutineJpaEntity;

import java.util.List;

public interface RoutineRepository {
    RoutineJpaEntity save(RoutineJpaEntity routine);
    List<RoutineJpaEntity> findAllByUserId(Long userId);
}
