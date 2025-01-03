package com.jaejoo.fitdocore.exercise.req;

import com.jaejoo.fitdomysql.domain.exercise.core.Exercise;
import com.jaejoo.fitdomysql.domain.exercise.core.ExerciseRecord;

import java.util.List;

public record RecordExerciseRecords(Long exerciseId, List<DailyExerciseRecordDto> records) {
    public Exercise toDomain() {
        Exercise exercise = new Exercise();
        for (DailyExerciseRecordDto recordDto : records) {
            exercise.add(new ExerciseRecord(recordDto.weight(), recordDto.count(), recordDto.set(), recordDto.progress()));
        }
        return exercise;
    }
}
