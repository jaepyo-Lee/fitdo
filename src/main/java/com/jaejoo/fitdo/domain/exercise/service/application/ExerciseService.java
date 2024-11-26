package com.jaejoo.fitdo.domain.exercise.service.application;

import com.jaejoo.fitdo.domain.exercise.infra.repository.CategoryQueryRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.ExerciseCommandRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.ExerciseQueryRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.ExerciseRoutineCommandRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.CategoryJpaEntity;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.enumerate.DeleteDelimiter;
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
    private final ExerciseRoutineCommandRepository exerciseRoutineCommandRepository;

    public String createExercise(ExerciseCreateCommand command) {
        CategoryJpaEntity categories = categoryQueryRepository.findById(command.getCategoryId());
        ExerciseJpaEntity exerciseJpaEntity = ExerciseJpaEntity.create(command.getExerciseName(), categories);
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
                    .categoryId(category.getId()).categoryName(category.getPartName()).exercises(exercises)
                    .build());
        }
        return exercisesWithCategory;
    }

    public void removeExercises(Long exerciseId) {
        ExerciseJpaEntity willRemoveEntity = exerciseQueryRepository.findById(exerciseId);
        ExerciseJpaEntity exerciseJpaEntity = ExerciseJpaEntity.builder()
                .category(willRemoveEntity.getCategory())
                .name(willRemoveEntity.getName())
                .id(willRemoveEntity.getId())
                .deleteDelimiter(DeleteDelimiter.DELETE)
                .build();
        ExerciseJpaEntity updateExerciseEntity = exerciseCommandRepository.save(exerciseJpaEntity);
        exerciseRoutineCommandRepository.deleteAllByExercise(updateExerciseEntity);
    }
}
