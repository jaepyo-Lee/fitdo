package com.jaejoo.fitdoweb.exercise.web;

import com.jaejoo.fitdocore.exercise.RoutineService;
import com.jaejoo.fitdocore.exercise.req.RoutineCreateCommand;
import com.jaejoo.fitdocore.exercise.res.ReadRoutineOfUser;
import com.jaejoo.fitdoweb.common.format.success.SuccessResponse;
import com.jaejoo.fitdoweb.common.mapper.ToResponseMapper;
import com.jaejoo.fitdoweb.exercise.web.req.RoutineCreateRequest;
import com.jaejoo.fitdoweb.exercise.web.res.ReadRoutinesOfUserResponse;
import com.jaejoo.fitdoweb.security.CustomUserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class RoutineController {
    private final RoutineService routineService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/v1/routines")
    public SuccessResponse createRoutine(@AuthenticationPrincipal CustomUserDetail userDetail,
                                         @RequestBody RoutineCreateRequest request) {
        routineService.create(new RoutineCreateCommand(userDetail.userId(), request.getName(), request.getExerciseIds()));
        return SuccessResponse.ok();
    }

    @GetMapping("/api/v1/routines")
    public SuccessResponse<List<ReadRoutinesOfUserResponse>> readRoutine(@AuthenticationPrincipal CustomUserDetail userDetail) {
        List<ReadRoutineOfUser> readRoutineOfUsers = routineService.readRoutine(userDetail.userId());
        List<ReadRoutinesOfUserResponse> response = ToResponseMapper.INSTANCE.toReadRoutineOfUserResponse(readRoutineOfUsers);
        return new SuccessResponse<>(response);
    }

    @DeleteMapping("/api/v1/routines/{routineId}")
    public SuccessResponse deleteRoutine(@PathVariable("routineId") Long routineId) {
        routineService.deleteRoutine(routineId);
        return SuccessResponse.ok();
    }
}
