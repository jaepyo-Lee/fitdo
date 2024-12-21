package com.jaejoo.fitdomysql.domain.exercise.infra.repository.impl;


import com.jaejoo.fitdomysql.domain.exercise.infra.repository.CategoryCommandRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.CategoryJpaRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.CategoryJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class CategoryCommandJpaRepository implements CategoryCommandRepository {
    private final CategoryJpaRepository repository;

    @Override
    public void saveAll(List<CategoryJpaEntity> categories) {
        repository.saveAll(categories);
    }
}
