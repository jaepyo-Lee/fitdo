package com.jaejoo.fitdoweb.common.mapper;

import com.jaejoo.fitdocore.exercise.res.ExercisesWithinCategory;
import com.jaejoo.fitdocore.exercise.res.FindExercisesWithCategory;
import com.jaejoo.fitdoweb.exercise.web.res.ExerciseReadResponse;
import com.jaejoo.fitdoweb.exercise.web.res.ExercisesWithinCategoryDto;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ReadExerciseMapper {
    ReadExerciseMapper INSTANCE = Mappers.getMapper(ReadExerciseMapper.class);
    @IterableMapping(qualifiedByName = "toExerciseReadResponse")
    List<ExerciseReadResponse> toExerciseReadResponse(List<FindExercisesWithCategory> findExercisesWithCategories);

    @Named("toExerciseReadResponse")
    ExerciseReadResponse toExerciseReadResponse(FindExercisesWithCategory findExercisesWithCategory);

    @IterableMapping(qualifiedByName = "toExerciseWithinCategoryDto")
    List<ExercisesWithinCategoryDto> toExerciseWithinCategoryDto(List<ExercisesWithinCategory> exercisesWithinCategory);

    @Named("toExerciseWithinCategoryDto")
    ExercisesWithinCategoryDto toExercisesWithinCategoryDto(ExercisesWithinCategory exercisesWithinCategory);
}
