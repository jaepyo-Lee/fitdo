package com.jaejoo.fitdo.domain.exercise.web;

import com.jaejoo.fitdo.domain.auth.service.domain.CustomUserDetail;
import com.jaejoo.fitdo.domain.exercise.service.application.ExerciseRecordService;
import com.jaejoo.fitdo.domain.exercise.service.application.req.DailyExerciseRecordDto;
import com.jaejoo.fitdo.domain.exercise.service.application.req.DailyRecordCreateCommand;
import com.jaejoo.fitdo.domain.exercise.service.application.res.FindMonthExerciseRecords;
import com.jaejoo.fitdo.domain.exercise.web.req.DailyRecordCreateRequest;
import com.jaejoo.fitdo.domain.exercise.web.req.dto.ExerciseRecordRequestDto;
import com.jaejoo.fitdo.domain.exercise.web.res.FindMonthExerciseRecordsResponse;
import com.jaejoo.fitdo.global.mapper.ToResponseMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ExerciseRecordController {
    private final ExerciseRecordService service;

    @PostMapping("/api/v1/exercise-record")
    public ResponseEntity<ResponseDto> createExerciseRecords(@AuthenticationPrincipal CustomUserDetail userDetail,
                                                             @RequestBody List<DailyRecordCreateRequest> requests) {
        List<DailyRecordCreateCommand> dailyRecordCreateCommands = new ArrayList<>();
        for (DailyRecordCreateRequest request : requests) {
            List<DailyExerciseRecordDto> list = new ArrayList<>();
            for (ExerciseRecordRequestDto record : request.getRecords()) {
                list.add(new DailyExerciseRecordDto(record.getSet(), record.getWeight(), record.getCount(), record.isProgress()));
            }
            dailyRecordCreateCommands.add(new DailyRecordCreateCommand(request.getTodayDate(), request.getExerciseId(), list));
        }
        boolean success = service.writeDailyExerciseFrom(userDetail.userId(), dailyRecordCreateCommands);

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

    @GetMapping("/api/v1/exercise")
    public ResponseEntity<List<FindMonthExerciseRecordsResponse>> getExerciseRecordsInMonth(@AuthenticationPrincipal CustomUserDetail userDetail,
                                                                                            @RequestParam(name = "yearMonth") YearMonth yearMonth) {
        List<FindMonthExerciseRecords> exerciseRecordsOfUserInMonth = service.findExerciseRecordsOfUserInMonth(userDetail.userId(), yearMonth);
        List<FindMonthExerciseRecordsResponse> findMonthExerciseRecordsResponse = ToResponseMapper.INSTANCE.toFindMonthExerciseRecordsResponse(exerciseRecordsOfUserInMonth);
        return ResponseEntity.ok(findMonthExerciseRecordsResponse);
    }
}
