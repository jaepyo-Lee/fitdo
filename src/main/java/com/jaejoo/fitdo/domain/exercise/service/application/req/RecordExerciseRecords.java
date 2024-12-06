package com.jaejoo.fitdo.domain.exercise.service.application.req;

import com.jaejoo.fitdo.domain.exercise.core.Exercise;
import com.jaejoo.fitdo.domain.exercise.core.ExerciseRecord;
import lombok.Getter;

import java.util.List;

@Getter
public class RecordExerciseRecords {
    private Long exerciseId;
    private List<DailyExerciseRecordDto> records;

    public Exercise toDomain() {
        Exercise exercise = new Exercise();
        for (DailyExerciseRecordDto recordDto : records) {
            exercise.add(new ExerciseRecord(recordDto.getWeight(), recordDto.getCount(), recordDto.getSet(), recordDto.isProgress()));
        }
        return exercise;
    }

    public RecordExerciseRecords(Long exerciseId, List<DailyExerciseRecordDto> records) {
        this.exerciseId = exerciseId;
        this.records = records;
    }
}
