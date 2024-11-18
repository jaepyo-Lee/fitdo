package com.jaejoo.fitdo.domain.exercise.web.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jaejoo.fitdo.domain.exercise.web.req.dto.ExerciseRecordRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DailyRecordCreateRequest {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate todayDate;
    private Long exerciseId;
    private List<ExerciseRecordRequestDto> records;
}
