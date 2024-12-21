package com.jaejoo.fitdomysql.domain.exercise.infra.repository;

import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.CategoryJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryCommandRepository {
    void saveAll(List<CategoryJpaEntity> categories);
}
