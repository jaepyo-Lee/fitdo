package com.jaejoo.fitdo.global.mapper;

import com.jaejoo.fitdo.domain.exercise.service.application.req.DailyExerciseRecordCreateCommand;
import com.jaejoo.fitdo.domain.exercise.service.application.req.DailyExerciseRecordDto;
import com.jaejoo.fitdo.domain.exercise.service.application.req.RecordExerciseRecords;
import com.jaejoo.fitdo.domain.exercise.web.req.DailyExerciseRecordsRequest;
import com.jaejoo.fitdo.domain.exercise.web.req.DailyRecordCreateRequest;
import com.jaejoo.fitdo.domain.exercise.web.req.dto.ExerciseRecordRequestDto;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ToServiceDtoMapper {
    ToServiceDtoMapper INSTANCE = Mappers.getMapper(ToServiceDtoMapper.class);

    @Named("toDailyExerciseRecordCreateCommand")
    @Mapping(source = "request.dailyExerciseRecords", target = "records")
    DailyExerciseRecordCreateCommand toDailyExerciseRecordCreateCommand(DailyRecordCreateRequest request);

    @IterableMapping(qualifiedByName = "toRecordExerciseRecords")
    List<RecordExerciseRecords> toRecordExerciseRecords(List<DailyExerciseRecordsRequest> requests);

    @Named("toRecordExerciseRecords")
    RecordExerciseRecords toRecordExerciseRecords(DailyExerciseRecordsRequest request);

    @IterableMapping(qualifiedByName = "toDailyExerciseRecordDto")
    List<DailyExerciseRecordDto> toDailyExerciseRecordDtos(List<ExerciseRecordRequestDto> recordExerciseRecords);

    @Named("toDailyExerciseRecordDto")
    DailyExerciseRecordDto toDailyExerciseRecordDto(ExerciseRecordRequestDto request);
}
