package com.jaejoo.fitdo.global.mapper;

import com.jaejoo.fitdo.domain.exercise.service.application.res.FindDateExerciseRecords;
import com.jaejoo.fitdo.domain.exercise.service.application.res.FindExerciseRecords;
import com.jaejoo.fitdo.domain.exercise.service.application.res.FindMonthExerciseRecords;
import com.jaejoo.fitdo.domain.exercise.web.res.FindDateExerciseRecordsResponseDto;
import com.jaejoo.fitdo.domain.exercise.web.res.FindExerciseRecordsResponseDto;
import com.jaejoo.fitdo.domain.exercise.web.res.FindMonthExerciseRecordsResponse;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ToResponseMapper {
    ToResponseMapper INSTANCE = Mappers.getMapper(ToResponseMapper.class);

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
