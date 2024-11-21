package com.jaejoo.fitdo.global.mapper;

import com.jaejoo.fitdo.domain.exercise.service.application.res.*;
import com.jaejoo.fitdo.domain.exercise.web.res.*;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ToResponseMapper {
    ToResponseMapper INSTANCE = Mappers.getMapper(ToResponseMapper.class);

    @IterableMapping(qualifiedByName = "toExerciseReadResponse")
    List<ExerciseReadResponse> toExerciseReadResponse(List<FindExercisesWithCategory> findExercisesWithCategories);

    @Named("toExerciseReadResponse")
    ExerciseReadResponse toExerciseReadResponse(FindExercisesWithCategory findExercisesWithCategory);


    @IterableMapping(qualifiedByName = "toExerciseWithinCategoryDto")
    List<ExercisesWithinCategoryDto> toExerciseWithinCategoryDto(List<ExercisesWithinCategory> exercisesWithinCategory);

    @Named("toExerciseWithinCategoryDto")
    ExercisesWithinCategoryDto toExercisesWithinCategoryDto(ExercisesWithinCategory exercisesWithinCategory);

    @IterableMapping(qualifiedByName = "toResponse")
    List<FindMonthExerciseRecordsResponse> toFindMonthExerciseRecordsResponse(List<FindMonthExerciseRecords> findMonthExerciseRecords);

    @Named("toResponse")
    FindMonthExerciseRecordsResponse toResponse(FindMonthExerciseRecords findMonthExerciseRecords);

    // 중첩 리스트 매핑
    @Named("toResponseDto1")
    FindDateExerciseRecordsResponseDto map(FindDateExerciseRecords dateRecord);

    @IterableMapping(qualifiedByName = "toResponseDto1")
    List<FindDateExerciseRecordsResponseDto> maps(List<FindDateExerciseRecords> dateRecords);

    @IterableMapping(qualifiedByName = "toResponseDto2")
    List<FindExerciseRecordsResponseDto> map(List<FindExerciseRecords> exerciseRecords);

    // 중첩 객체 매핑
    @Named("toResponseDto2")
    FindExerciseRecordsResponseDto map(FindExerciseRecords exerciseRecord);
}
