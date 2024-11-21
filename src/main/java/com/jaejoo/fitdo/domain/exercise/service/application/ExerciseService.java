package com.jaejoo.fitdo.domain.exercise.service.application;

import com.jaejoo.fitdo.domain.exercise.infra.repository.CategoryQueryRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.ExerciseCommandRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.CategoryJpaEntity;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.ExerciseJpaEntity;
import com.jaejoo.fitdo.domain.exercise.service.application.req.ExerciseCreateCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ExerciseService {
    private final CategoryQueryRepository categoryQueryRepository;
    private final ExerciseCommandRepository exerciseQueryRepository;

    public String createExercise(ExerciseCreateCommand command) {
        CategoryJpaEntity categories = categoryQueryRepository.findById(command.getCategoryId());
        ExerciseJpaEntity exerciseJpaEntity = ExerciseJpaEntity.builder().name(command.getExerciseName())
                .category(categories)
                .build();
        ExerciseJpaEntity save = exerciseQueryRepository.save(exerciseJpaEntity);
        return save.getName();
    }
}
