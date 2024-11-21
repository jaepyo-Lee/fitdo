package com.jaejoo.fitdo.domain.exercise.infra.repository.impl;

import com.jaejoo.fitdo.domain.exercise.infra.repository.CategoryQueryRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.CategoryJpaRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.CategoryJpaEntity;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.entity.UserJpaEntity;
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
}
