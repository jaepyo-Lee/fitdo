package com.jaejoo.fitdocore.exercise;

import com.jaejoo.fitdocore.exercise.req.RoutineCreateCommand;
import com.jaejoo.fitdocore.exercise.res.CategoryAndExerciseWithinRoutine;
import com.jaejoo.fitdocore.exercise.res.ReadRoutineOfUser;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.ExerciseQueryRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.ExerciseRoutineCommandRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.ExerciseRoutineQueryRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.RoutineRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.CategoryJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.ExerciseJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.ExerciseRoutineJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.RoutineJpaEntity;
import com.jaejoo.fitdomysql.domain.user.core.User;
import com.jaejoo.fitdomysql.domain.user.repository.UserRepository;
import com.jaejoo.fitdomysql.domain.user.repository.jpa.entity.UserJpaEntity;
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
    private final UserRepository userRepository;
    private final ExerciseRoutineQueryRepository exerciseRoutineQueryRepository;

    public void create(RoutineCreateCommand command) {
        User user = userRepository.findById(command.userId());
        RoutineJpaEntity routine = new RoutineJpaEntity(command.name(), UserJpaEntity.from(user));
        RoutineJpaEntity saveRoutine = routineRepository.save(routine);
        List<ExerciseJpaEntity> exercises = exerciseQueryRepository.findAllByIds(command.exerciseIds());
        List<ExerciseRoutineJpaEntity> exerciseRoutines = new ArrayList<>();
        for (ExerciseJpaEntity saveExercise : exercises) {
            exerciseRoutines.add(new ExerciseRoutineJpaEntity(saveRoutine, saveExercise));
        }
        exerciseRoutineCommandRepository.saveAll(exerciseRoutines);
    }

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

    public void deleteRoutine(Long routineId){
        RoutineJpaEntity routine = routineRepository.findById(routineId);
        exerciseRoutineCommandRepository.deleteAllByRoutine(routine);
        routineRepository.deleteBy(routine);
    }
}
