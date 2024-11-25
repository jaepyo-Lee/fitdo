package com.jaejoo.fitdo.domain.exercise.infra.repository.impl;

import com.jaejoo.fitdo.domain.exercise.infra.repository.RoutineRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.RoutineJpaRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.RoutineJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoutineCommandJpaRepository implements RoutineRepository {
    private final RoutineJpaRepository routineJpaRepository;

    @Override
    public RoutineJpaEntity save(RoutineJpaEntity routine) {
        return routineJpaRepository.save(routine);
    }
}
