package com.jaejoo.fitdoweb.common.mapper;

import com.jaejoo.fitdocore.exercise.req.DailyExerciseRecordCreateCommand;
import com.jaejoo.fitdocore.exercise.req.DailyExerciseRecordDto;
import com.jaejoo.fitdocore.exercise.req.RecordExerciseRecords;
import com.jaejoo.fitdoweb.exercise.web.req.DailyExerciseRecordsRequest;
import com.jaejoo.fitdoweb.exercise.web.req.DailyRecordCreateRequest;
import com.jaejoo.fitdoweb.exercise.web.req.dto.ExerciseRecordRequestDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-29T01:27:10+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.11 (Amazon.com Inc.)"
)
public class ToServiceDtoMapperImpl implements ToServiceDtoMapper {

    @Override
    public DailyExerciseRecordCreateCommand toDailyExerciseRecordCreateCommand(DailyRecordCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        DailyExerciseRecordCreateCommand.DailyExerciseRecordCreateCommandBuilder dailyExerciseRecordCreateCommand = DailyExerciseRecordCreateCommand.builder();

        dailyExerciseRecordCreateCommand.recordDate( request.getRecordDate() );
        dailyExerciseRecordCreateCommand.records( toRecordExerciseRecords( request.getDailyExerciseRecords() ) );

        return dailyExerciseRecordCreateCommand.build();
    }

    @Override
    public List<RecordExerciseRecords> toRecordExerciseRecords(List<DailyExerciseRecordsRequest> requests) {
        if ( requests == null ) {
            return null;
        }

        List<RecordExerciseRecords> list = new ArrayList<RecordExerciseRecords>( requests.size() );
        for ( DailyExerciseRecordsRequest dailyExerciseRecordsRequest : requests ) {
            list.add( toRecordExerciseRecords( dailyExerciseRecordsRequest ) );
        }

        return list;
    }

    @Override
    public RecordExerciseRecords toRecordExerciseRecords(DailyExerciseRecordsRequest request) {
        if ( request == null ) {
            return null;
        }

        List<DailyExerciseRecordDto> records = null;
        Long exerciseId = null;

        records = toDailyExerciseRecordDtos( request.getRecords() );
        exerciseId = request.getExerciseId();

        RecordExerciseRecords recordExerciseRecords = new RecordExerciseRecords( exerciseId, records );

        return recordExerciseRecords;
    }

    @Override
    public List<DailyExerciseRecordDto> toDailyExerciseRecordDtos(List<ExerciseRecordRequestDto> recordExerciseRecords) {
        if ( recordExerciseRecords == null ) {
            return null;
        }

        List<DailyExerciseRecordDto> list = new ArrayList<DailyExerciseRecordDto>( recordExerciseRecords.size() );
        for ( ExerciseRecordRequestDto exerciseRecordRequestDto : recordExerciseRecords ) {
            list.add( toDailyExerciseRecordDto( exerciseRecordRequestDto ) );
        }

        return list;
    }

    @Override
    public DailyExerciseRecordDto toDailyExerciseRecordDto(ExerciseRecordRequestDto request) {
        if ( request == null ) {
            return null;
        }

        int set = 0;
        int weight = 0;
        int count = 0;
        boolean progress = false;

        set = request.getSet();
        weight = request.getWeight();
        count = request.getCount();
        progress = request.isProgress();

        DailyExerciseRecordDto dailyExerciseRecordDto = new DailyExerciseRecordDto( set, weight, count, progress );

        return dailyExerciseRecordDto;
    }
}
