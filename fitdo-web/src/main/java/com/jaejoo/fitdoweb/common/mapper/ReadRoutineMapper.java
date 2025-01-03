package com.jaejoo.fitdoweb.common.mapper;

import com.jaejoo.fitdocore.exercise.res.CategoryAndExerciseWithinRoutine;
import com.jaejoo.fitdocore.exercise.res.ReadRoutineOfUser;
import com.jaejoo.fitdoweb.exercise.web.res.CategoryAndExerciseWithinRoutineDto;
import com.jaejoo.fitdoweb.exercise.web.res.ReadRoutinesOfUserResponse;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ReadRoutineMapper {
    ReadRoutineMapper INSTANCE = Mappers.getMapper(ReadRoutineMapper.class);
    @IterableMapping(qualifiedByName = "toReadRoutineOfUserResponse")
    List<ReadRoutinesOfUserResponse> toReadRoutineOfUserResponse(List<ReadRoutineOfUser> dto);

    @Named("toReadRoutineOfUserResponse")
    ReadRoutinesOfUserResponse toReadRoutineOfUserResponse(ReadRoutineOfUser dto);

    @Named("toCategoryAndExerciseWithinRoutine")
    CategoryAndExerciseWithinRoutineDto toCategoryAndExerciseWithinRoutine(CategoryAndExerciseWithinRoutine dto);

    @IterableMapping(qualifiedByName = "toCategoryAndExerciseWithinRoutine")
    List<CategoryAndExerciseWithinRoutineDto> toCategoryAndExerciseWithinRoutine(List<CategoryAndExerciseWithinRoutine> dto);
}
