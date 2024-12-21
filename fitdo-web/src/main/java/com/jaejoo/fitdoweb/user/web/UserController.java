package com.jaejoo.fitdoweb.user.web;

import com.jaejoo.fitdocore.user.UserService;
import com.jaejoo.fitdocore.user.req.NickNameIsDuplicateCommand;
import com.jaejoo.fitdocore.user.res.IsNickNameDuplicateResult;
import com.jaejoo.fitdoweb.common.format.success.SuccessResponse;
import com.jaejoo.fitdoweb.security.CustomUserDetail;
import com.jaejoo.fitdoweb.user.web.req.NickNameIsDuplicateRequest;
import com.jaejoo.fitdoweb.user.web.req.UserRegisterRequest;
import com.jaejoo.fitdoweb.user.web.res.IsNickNameDuplicateResponse;
import com.jaejoo.fitdoweb.user.web.res.UserRegisterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping("/api/v1/user/register")
    public ResponseEntity<UserRegisterResponse> completeRegister(@AuthenticationPrincipal CustomUserDetail userDetail,
                                                                 @RequestBody UserRegisterRequest request) {
        boolean isCompleteSignUp = userService.completeSignUp(request.toCommand(userDetail.userId()));
        return ResponseEntity.ok(new UserRegisterResponse(isCompleteSignUp));
    }

    @PostMapping("/api/v1/nickname/duplicate")
    public SuccessResponse<IsNickNameDuplicateResponse> duplicateNickname(@RequestBody NickNameIsDuplicateRequest request) {
        IsNickNameDuplicateResult result = userService.isDuplicate(new NickNameIsDuplicateCommand(request.getWillInspectNickname()));
        return new SuccessResponse<>(new IsNickNameDuplicateResponse(result.isDuplicateNickname()));
    }
}
