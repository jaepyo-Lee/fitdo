package com.jaejoo.fitdo.domain.exercise.service.application.req;

import com.jaejoo.fitdo.domain.exercise.core.ExerciseRecord;
import com.jaejoo.fitdo.domain.exercise.core.ExerciseRecords;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class DailyRecordCreateCommand {
    private LocalDate recordDate;
    private Long exerciseId;
    private List<DailyExerciseRecordDto> recordDtos;

    public DailyRecordCreateCommand(LocalDate recordDate, Long exerciseId, List<DailyExerciseRecordDto> recordDtos) {
        this.recordDate = recordDate;
        this.exerciseId = exerciseId;
        this.recordDtos = recordDtos;
    }

    public ExerciseRecords toDomain() {
        ExerciseRecords exerciseRecords = new ExerciseRecords();
        for (DailyExerciseRecordDto recordDto : recordDtos) {
            exerciseRecords.add(new ExerciseRecord(recordDto.getWeight(), recordDto.getCount(), recordDto.getSet(), recordDto.isProgress()));
        }
        return exerciseRecords;
    }
}
