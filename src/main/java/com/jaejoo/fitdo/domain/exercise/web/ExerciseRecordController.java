package com.jaejoo.fitdo.domain.exercise.web;

import com.jaejoo.fitdo.domain.auth.service.domain.CustomUserDetail;
import com.jaejoo.fitdo.domain.exercise.service.application.ExerciseRecordService;
import com.jaejoo.fitdo.domain.exercise.service.application.req.DailyExerciseRecordCreateCommand;
import com.jaejoo.fitdo.domain.exercise.service.application.res.FindMonthExerciseRecords;
import com.jaejoo.fitdo.domain.exercise.service.application.res.ProgressPercentage;
import com.jaejoo.fitdo.domain.exercise.web.req.DailyRecordCreateRequest;
import com.jaejoo.fitdo.domain.exercise.web.res.FindMonthExerciseRecordsResponse;
import com.jaejoo.fitdo.domain.exercise.web.res.ProgressPercentageWithMonthInfoResponse;
import com.jaejoo.fitdo.global.format.success.SuccessResponse;
import com.jaejoo.fitdo.global.mapper.ToResponseMapper;
import com.jaejoo.fitdo.global.mapper.ToServiceDtoMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ExerciseRecordController {
    private final ExerciseRecordService service;

    @PostMapping("/api/v1/exercise-record")
    public ResponseEntity<ResponseDto> createExerciseRecords(@AuthenticationPrincipal CustomUserDetail userDetail,
                                                             @RequestBody DailyRecordCreateRequest requests) {
        DailyExerciseRecordCreateCommand command = ToServiceDtoMapper.INSTANCE.toDailyExerciseRecordCreateCommand(requests);
        boolean success = service.writeDailyExerciseFrom(userDetail.userId(), command);
        return ResponseEntity.ok(new ResponseDto(success));
    }

    @Getter
    @NoArgsConstructor
    static class ResponseDto {
        Boolean result;

        public ResponseDto(Boolean result) {
            this.result = result;
        }
    }

    @GetMapping("/api/v1/exercises/records")
    public SuccessResponse<FindMonthExerciseRecordsResponse> getExerciseRecordsInMonth(@AuthenticationPrincipal CustomUserDetail userDetail,
                                                                                            @RequestParam(name = "date") LocalDate date) {
        FindMonthExerciseRecords exerciseRecordsOfUserInMonth = service.findExerciseRecordsOfUserAtDate(userDetail.userId(), date);
        FindMonthExerciseRecordsResponse findMonthExerciseRecordsResponse = ToResponseMapper.INSTANCE.toResponse(exerciseRecordsOfUserInMonth);
        return new SuccessResponse<>(findMonthExerciseRecordsResponse);
    }

    @GetMapping("/api/v1/exercises/percentage")
    public SuccessResponse<ProgressPercentageWithMonthInfoResponse> readExerciseProgressPercentageInMonth(@AuthenticationPrincipal CustomUserDetail userDetail,
                                                                                                          @RequestParam("yearMonth") YearMonth yearMonth) {
        List<ProgressPercentage> progressPercentages = service.calculateProgressPercentageInMonth(userDetail.userId(), yearMonth);
        LocalDate startDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
        DayOfWeek dayOfWeek = startDate.getDayOfWeek();
        int value = dayOfWeek.getValue();
        ProgressPercentageWithMonthInfoResponse response = ToResponseMapper.INSTANCE.toProgressPercentageWithMonthInfoResponse(value, progressPercentages);
        return new SuccessResponse<>(response);
    }
}
