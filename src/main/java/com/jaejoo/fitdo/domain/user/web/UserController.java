package com.jaejoo.fitdo.domain.user.web;

import com.jaejoo.fitdo.domain.auth.service.domain.CustomUserDetail;
import com.jaejoo.fitdo.domain.user.core.User;
import com.jaejoo.fitdo.domain.user.service.application.UserService;
import com.jaejoo.fitdo.domain.user.service.application.req.NickNameIsDuplicateCommand;
import com.jaejoo.fitdo.domain.user.service.application.res.IsNickNameDuplicateResult;
import com.jaejoo.fitdo.domain.user.web.req.NickNameIsDuplicateRequest;
import com.jaejoo.fitdo.domain.user.web.req.UserRegisterRequest;
import com.jaejoo.fitdo.domain.user.web.res.IsNickNameDuplicateResponse;
import com.jaejoo.fitdo.domain.user.web.res.UserRegisterResponse;
import com.jaejoo.fitdo.global.format.success.SuccessResponse;
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
        User user = userService.completeSignUp(request.toCommand(userDetail.userId()));
        return ResponseEntity.ok(new UserRegisterResponse(user.getAccount().getNewFlag()));
    }

    @PostMapping("/api/v1/nickname/duplicate")
    public SuccessResponse<IsNickNameDuplicateResponse> duplicateNickname(@RequestBody NickNameIsDuplicateRequest request) {
        IsNickNameDuplicateResult result = userService.isDuplicate(new NickNameIsDuplicateCommand(request.getWillInspectNickname()));
        return new SuccessResponse<>(new IsNickNameDuplicateResponse(result.isDuplicateNickname()));
    }
}
