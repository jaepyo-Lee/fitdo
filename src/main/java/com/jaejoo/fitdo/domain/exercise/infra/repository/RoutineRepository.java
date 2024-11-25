package com.jaejoo.fitdo.domain.exercise.infra.repository;

import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.RoutineJpaEntity;

public interface RoutineRepository {
    RoutineJpaEntity save(RoutineJpaEntity routine);
}
