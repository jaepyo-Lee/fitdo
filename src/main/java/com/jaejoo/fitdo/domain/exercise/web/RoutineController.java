package com.jaejoo.fitdo.domain.exercise.web;

import com.jaejoo.fitdo.domain.exercise.service.application.RoutineService;
import com.jaejoo.fitdo.domain.exercise.service.application.req.RoutineCreateCommand;
import com.jaejoo.fitdo.domain.exercise.web.req.RoutineCreateRequest;
import com.jaejoo.fitdo.global.format.success.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class RoutineController {
    private final RoutineService routineService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/v1/routine")
    public SuccessResponse getExerciseRecordsInMonth(@RequestBody RoutineCreateRequest request) {
        routineService.create(RoutineCreateCommand.from(request));
        return SuccessResponse.ok();
    }
}
