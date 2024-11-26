package com.jaejoo.fitdo.domain.exercise.infra.repository.impl;

import com.jaejoo.fitdo.domain.exercise.infra.repository.RoutineRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.RoutineJpaRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.RoutineJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RoutineCommandJpaRepository implements RoutineRepository {
    private final RoutineJpaRepository routineJpaRepository;

    @Override
    public RoutineJpaEntity save(RoutineJpaEntity routine) {
        return routineJpaRepository.save(routine);
    }

    @Override
    public List<RoutineJpaEntity> findAllByUserId(Long userId) {
        return routineJpaRepository.findAllByUserId(userId);
    }

    @Override
    public void deleteBy(RoutineJpaEntity routine) {
        routineJpaRepository.delete(routine);
    }

    @Override
    public RoutineJpaEntity findById(Long routineId) {
        return routineJpaRepository.findById(routineId)
                .orElseThrow(()->new IllegalArgumentException("not found routine"));
    }
}
