package com.jaejoo.fitdoweb.common.mapper;

import com.jaejoo.fitdocore.exercise.req.DailyExerciseRecordCreateCommand;
import com.jaejoo.fitdocore.exercise.req.DailyExerciseRecordDto;
import com.jaejoo.fitdocore.exercise.req.RecordExerciseRecords;
import com.jaejoo.fitdoweb.exercise.web.req.DailyExerciseRecordsRequest;
import com.jaejoo.fitdoweb.exercise.web.req.DailyRecordCreateRequest;
import com.jaejoo.fitdoweb.exercise.web.req.dto.ExerciseRecordRequestDto;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CreateExerciseRecordsMapper {
    CreateExerciseRecordsMapper INSTANCE = Mappers.getMapper(CreateExerciseRecordsMapper.class);

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
