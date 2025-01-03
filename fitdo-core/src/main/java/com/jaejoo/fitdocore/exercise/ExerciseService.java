package com.jaejoo.fitdocore.exercise;


import com.jaejoo.fitdocore.exercise.req.ExerciseCreateCommand;
import com.jaejoo.fitdocore.exercise.res.ExercisesWithinCategory;
import com.jaejoo.fitdocore.exercise.res.FindExercisesWithCategory;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.CategoryQueryRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.ExerciseCommandRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.ExerciseQueryRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.CategoryJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.ExerciseJpaEntity;
import com.jaejoo.fitdomysql.domain.user.core.User;
import com.jaejoo.fitdomysql.domain.user.repository.UserRepository;
import com.jaejoo.fitdomysql.domain.user.repository.jpa.entity.UserJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ExerciseService {
    private final CategoryQueryRepository categoryQueryRepository;
    private final ExerciseCommandRepository exerciseCommandRepository;
    private final ExerciseQueryRepository exerciseQueryRepository;
    private final UserRepository userRepository;

    @Transactional
    public String createExercise(ExerciseCreateCommand command) {
        CategoryJpaEntity categories = categoryQueryRepository.findById(command.categoryId());
        User user = userRepository.findById(command.userId());
        ExerciseJpaEntity exerciseJpaEntity = ExerciseJpaEntity.create(UserJpaEntity.from(user), command.exerciseName(), categories);
        ExerciseJpaEntity save = exerciseCommandRepository.save(exerciseJpaEntity);
        return save.getName();
    }

    @Transactional(readOnly = true)
    public List<FindExercisesWithCategory> findExercisesWithCategoryFor(Long userId) {
        List<CategoryJpaEntity> categories = categoryQueryRepository.findAll();
        return categories.stream()
                .map(category -> createFindExercisesWithCategory(userId, category))
                .collect(Collectors.toList());
    }

    private FindExercisesWithCategory createFindExercisesWithCategory(Long userId, CategoryJpaEntity category) {
        List<ExerciseJpaEntity> exercisesByCategory = exerciseQueryRepository.findExercisesByCategoryAndUserId(category, userId);
        List<ExercisesWithinCategory> exercises = convertToExercisesWithinCategory(exercisesByCategory);
        return FindExercisesWithCategory.builder()
                .categoryId(category.getId())
                .categoryName(category.getPartName())
                .exercises(exercises)
                .build();
    }

    private static List<ExercisesWithinCategory> convertToExercisesWithinCategory(List<ExerciseJpaEntity> exercisesByCategory) {
        return exercisesByCategory.stream()
                .map(exercise -> ExercisesWithinCategory.builder()
                        .exerciseName(exercise.getName())
                        .exerciseId(exercise.getId())
                        .build())
                .collect(Collectors.toList());
    }


    @Transactional
    public void removeExercises(Long exerciseId) {
        ExerciseJpaEntity deleteTarget = exerciseQueryRepository.findById(exerciseId);
        deleteTarget.delete();
        exerciseCommandRepository.save(deleteTarget);
    }
}
