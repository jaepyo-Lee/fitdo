package com.jaejoo.fitdoweb.exercise.web;

import com.jaejoo.fitdocore.exercise.ExerciseService;
import com.jaejoo.fitdocore.exercise.res.FindExercisesWithCategory;
import com.jaejoo.fitdoweb.common.format.success.SuccessResponse;
import com.jaejoo.fitdoweb.common.mapper.ToResponseMapper;
import com.jaejoo.fitdoweb.exercise.web.req.ExerciseCreateRequest;
import com.jaejoo.fitdoweb.exercise.web.res.ExerciseCreateResponse;
import com.jaejoo.fitdoweb.exercise.web.res.ExerciseReadResponse;
import com.jaejoo.fitdoweb.security.CustomUserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ExerciseController {
    private final ExerciseService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/v1/exercises")
    public SuccessResponse<ExerciseCreateResponse> create(@RequestBody ExerciseCreateRequest exerciseCreateRequest) {
        String exercise = service.createExercise(exerciseCreateRequest.toCommand());
        return new SuccessResponse<>(HttpStatus.CREATED.value(), new ExerciseCreateResponse(exercise));
    }

    @GetMapping("/api/v1/exercises")
    public SuccessResponse<List<ExerciseReadResponse>> read(@AuthenticationPrincipal CustomUserDetail userDetail) {
        List<FindExercisesWithCategory> exercisesWithCategoryOf = service.findExercisesWithCategoryOf(userDetail.userId());
        List<ExerciseReadResponse> exerciseReadResponse = ToResponseMapper.INSTANCE.toExerciseReadResponse(exercisesWithCategoryOf);
        return new SuccessResponse<>(HttpStatus.OK.value(), exerciseReadResponse);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/api/v1/exercises/{exerciseId}")
    public SuccessResponse delete(@PathVariable("exerciseId") Long exerciseId) {
        service.removeExercises(exerciseId);
        return SuccessResponse.ok();
    }
}
