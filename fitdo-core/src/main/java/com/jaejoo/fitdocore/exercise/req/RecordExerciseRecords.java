package com.jaejoo.fitdocore.exercise.req;

import java.util.List;

public record RecordExerciseRecords(Long exerciseId, List<DailyExerciseRecordDto> sets) {
}
