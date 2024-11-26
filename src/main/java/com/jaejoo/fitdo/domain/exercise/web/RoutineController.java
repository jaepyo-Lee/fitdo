package com.jaejoo.fitdo.domain.exercise.web;

import com.jaejoo.fitdo.domain.auth.service.domain.CustomUserDetail;
import com.jaejoo.fitdo.domain.exercise.service.application.RoutineService;
import com.jaejoo.fitdo.domain.exercise.service.application.req.RoutineCreateCommand;
import com.jaejoo.fitdo.domain.exercise.service.application.res.ReadRoutineOfUser;
import com.jaejoo.fitdo.domain.exercise.web.req.RoutineCreateRequest;
import com.jaejoo.fitdo.domain.exercise.web.res.ReadRoutinesOfUserResponse;
import com.jaejoo.fitdo.global.format.success.SuccessResponse;
import com.jaejoo.fitdo.global.mapper.ToResponseMapper;
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
        routineService.create(RoutineCreateCommand.from(userDetail.userId(), request));
        return SuccessResponse.ok();
    }

    @GetMapping("/api/v1/routines")
    public SuccessResponse<List<ReadRoutinesOfUserResponse>> readRoutine(@AuthenticationPrincipal CustomUserDetail userDetail) {
        List<ReadRoutineOfUser> readRoutineOfUsers = routineService.readRoutine(userDetail.userId());
        List<ReadRoutinesOfUserResponse> response = ToResponseMapper.INSTANCE.toReadRoutineOfUserResponse(readRoutineOfUsers);
        return new SuccessResponse<>(response);
    }

    @DeleteMapping("/api/v1/routines/{routineId}")
    public SuccessResponse deleteRoutine(@PathVariable("routineId")Long routineId){
        routineService.deleteRoutine(routineId);
        return SuccessResponse.ok();
    }
}
