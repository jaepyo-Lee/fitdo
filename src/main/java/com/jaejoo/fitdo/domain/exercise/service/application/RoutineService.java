package com.jaejoo.fitdo.domain.exercise.service.application;

import com.jaejoo.fitdo.domain.exercise.infra.repository.ExerciseQueryRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.ExerciseRoutineCommandRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.RoutineRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.ExerciseJpaEntity;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.ExerciseRoutineJpaEntity;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.RoutineJpaEntity;
import com.jaejoo.fitdo.domain.exercise.service.application.req.RoutineCreateCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RoutineService {
    private final ExerciseQueryRepository exerciseQueryRepository;
    private final ExerciseRoutineCommandRepository exerciseRoutineCommandRepository;
    private final RoutineRepository routineRepository;

    public void create(RoutineCreateCommand command) {
        RoutineJpaEntity routine = new RoutineJpaEntity(command.name());
        RoutineJpaEntity saveRoutine = routineRepository.save(routine);
        List<ExerciseJpaEntity> exercises = exerciseQueryRepository.findAllByIds(command.exerciseIds());
        List<ExerciseRoutineJpaEntity> exerciseRoutines = new ArrayList<>();
        for (ExerciseJpaEntity saveExercise : exercises) {
            exerciseRoutines.add(new ExerciseRoutineJpaEntity(saveRoutine, saveExercise));
        }
        exerciseRoutineCommandRepository.saveAll(exerciseRoutines);
    }
}
