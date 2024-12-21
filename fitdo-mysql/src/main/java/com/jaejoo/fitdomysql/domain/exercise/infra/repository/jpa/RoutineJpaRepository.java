package com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa;

import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.RoutineJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoutineJpaRepository extends JpaRepository<RoutineJpaEntity, Long> {
    List<RoutineJpaEntity> findAllByUserId(Long userId);
}
