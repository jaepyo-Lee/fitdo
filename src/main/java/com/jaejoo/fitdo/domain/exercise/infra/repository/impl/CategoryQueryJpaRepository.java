package com.jaejoo.fitdo.domain.exercise.infra.repository.impl;

import com.jaejoo.fitdo.domain.exercise.infra.repository.CategoryQueryRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.CategoryJpaRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.CategoryJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryQueryJpaRepository implements CategoryQueryRepository {
    private final CategoryJpaRepository repository;

    @Override
    public CategoryJpaEntity findById(Long id) {
        return repository.findById(id).orElseThrow(()->new IllegalArgumentException("not found category"));
    }

    @Override
    public List<CategoryJpaEntity> findAllByUserId(Long userId) {
        return repository.findAllByUserId(userId);
    }
}
