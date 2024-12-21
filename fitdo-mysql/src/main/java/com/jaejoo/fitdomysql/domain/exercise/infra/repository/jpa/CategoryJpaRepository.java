package com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa;


import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.CategoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryJpaRepository extends JpaRepository<CategoryJpaEntity, Long> {
    Optional<CategoryJpaEntity> findById(Long id);
}
