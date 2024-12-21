package com.jaejoo.fitdomysql.domain.exercise.infra.repository;

import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.CategoryJpaEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CategoryQueryRepository {
    CategoryJpaEntity findById(Long id);

    List<CategoryJpaEntity> findAll();
}
