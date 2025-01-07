package com.jaejoo.fitdocore.exercise;

import com.jaejoo.fitdocore.exercise.res.CategoryAndExerciseWithinRoutine;
import com.jaejoo.fitdocore.exercise.res.ReadRoutineOfUser;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.ExerciseRoutineQueryRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.RoutineRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.CategoryJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.ExerciseJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.ExerciseRoutineJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.RoutineJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RoutineReadService {
    private final RoutineRepository routineRepository;
    private final ExerciseRoutineQueryRepository exerciseRoutineQueryRepository;

    public List<ReadRoutineOfUser> readRoutine(Long userId) {
        List<RoutineJpaEntity> routinesOfUser = routineRepository.findAllByUserId(userId);
        List<ReadRoutineOfUser> routineInfosOfUser = new ArrayList<>();
        for (RoutineJpaEntity routine : routinesOfUser) {
            List<ExerciseRoutineJpaEntity> ExerciseRoutine = exerciseRoutineQueryRepository.findAllByRoutine(routine);
            List<CategoryAndExerciseWithinRoutine> categoryAndExercise = new ArrayList<>();
            for (ExerciseRoutineJpaEntity exerciseRoutine : ExerciseRoutine) {
                ExerciseJpaEntity exercise = exerciseRoutine.getExercise();
                CategoryJpaEntity category = exercise.getCategory();
                categoryAndExercise.add(new CategoryAndExerciseWithinRoutine(category.getId(), category.getPartName(), exercise.getId(), exercise.getName()));
            }
            routineInfosOfUser.add(new ReadRoutineOfUser(routine.getId(), routine.getName(), categoryAndExercise));
        }
        return routineInfosOfUser;
    }
}
