package com.jaejoo.fitdoweb.common.mapper;

import com.jaejoo.fitdocore.exercise.res.FindDateExerciseRecords;
import com.jaejoo.fitdocore.exercise.res.FindExerciseRecords;
import com.jaejoo.fitdocore.exercise.res.FindMonthExerciseRecords;
import com.jaejoo.fitdoweb.exercise.web.res.FindDateExerciseRecordsResponseDto;
import com.jaejoo.fitdoweb.exercise.web.res.FindExerciseRecordsResponseDto;
import com.jaejoo.fitdoweb.exercise.web.res.FindMonthExerciseRecordsResponse;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ReadExerciseRecordsInMonthMapper {
    ReadExerciseRecordsInMonthMapper INSTANCE = Mappers.getMapper(ReadExerciseRecordsInMonthMapper.class);
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
