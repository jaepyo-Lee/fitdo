package com.jaejoo.fitdo.domain.exercise.web;

import com.jaejoo.fitdo.domain.exercise.service.application.ExerciseService;
import com.jaejoo.fitdo.domain.exercise.web.req.ExerciseCreateRequest;
import com.jaejoo.fitdo.domain.exercise.web.res.ExerciseCreateResponse;
import com.jaejoo.fitdo.global.format.success.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExerciseController {
    private final ExerciseService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/v1/exercise")
    public SuccessResponse<ExerciseCreateResponse> create(@RequestBody ExerciseCreateRequest exerciseCreateRequest) {
        String exercise = service.createExercise(exerciseCreateRequest.toCommand());
        return new SuccessResponse<>(HttpStatus.CREATED.value(), new ExerciseCreateResponse(exercise));
    }
}
