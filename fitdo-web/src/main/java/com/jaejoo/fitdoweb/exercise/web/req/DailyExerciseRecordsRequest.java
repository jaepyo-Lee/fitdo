package com.jaejoo.fitdoweb.exercise.web.req;

import com.jaejoo.fitdoweb.exercise.web.req.dto.ExerciseRecordRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DailyExerciseRecordsRequest {
    private Long exerciseId;
    private List<ExerciseRecordRequestDto> records;
}
