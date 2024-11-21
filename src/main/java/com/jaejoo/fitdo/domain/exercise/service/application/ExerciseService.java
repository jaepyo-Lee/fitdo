package com.jaejoo.fitdo.domain.exercise.service.application;

import com.jaejoo.fitdo.domain.exercise.infra.repository.CategoryQueryRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.ExerciseCommandRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.ExerciseQueryRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.CategoryJpaEntity;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.ExerciseJpaEntity;
import com.jaejoo.fitdo.domain.exercise.service.application.req.ExerciseCreateCommand;
import com.jaejoo.fitdo.domain.exercise.service.application.res.ExercisesWithinCategory;
import com.jaejoo.fitdo.domain.exercise.service.application.res.FindExercisesWithCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ExerciseService {
    private final CategoryQueryRepository categoryQueryRepository;
    private final ExerciseCommandRepository exerciseCommandRepository;
    private final ExerciseQueryRepository exerciseQueryRepository;

    public String createExercise(ExerciseCreateCommand command) {
        CategoryJpaEntity categories = categoryQueryRepository.findById(command.getCategoryId());
        ExerciseJpaEntity exerciseJpaEntity = ExerciseJpaEntity.builder().name(command.getExerciseName())
                .category(categories)
                .build();
        ExerciseJpaEntity save = exerciseCommandRepository.save(exerciseJpaEntity);
        return save.getName();
    }

    public List<FindExercisesWithCategory> findExercisesWithCategoryOf(Long userId) {
        List<FindExercisesWithCategory> exercisesWithCategory = new ArrayList<>();
        List<CategoryJpaEntity> categories = categoryQueryRepository.findAllByUserId(userId);
        for (CategoryJpaEntity category : categories) {
            List<ExerciseJpaEntity> exercisesByCategory = exerciseQueryRepository.findExercisesByCategory(category);
            List<ExercisesWithinCategory> exercises = new ArrayList<>();
            for (ExerciseJpaEntity exerciseJpaEntity : exercisesByCategory) {
                exercises.add(ExercisesWithinCategory.builder()
                        .exerciseName(exerciseJpaEntity.getName()).exerciseId(exerciseJpaEntity.getId())
                        .build());
            }
            exercisesWithCategory.add(FindExercisesWithCategory.builder()
                    .categoryId(category.getId()).categoryName(category.getCategoryName()).exercises(exercises)
                    .build());
        }
        return exercisesWithCategory;
    }

    public void removeExercises(Long exerciseId){
        exerciseCommandRepository.deleteById(exerciseId);
    }
}
