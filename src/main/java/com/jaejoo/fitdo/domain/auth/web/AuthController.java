package com.jaejoo.fitdo.domain.auth.web;

import com.jaejoo.fitdo.domain.auth.service.application.AuthService;
import com.jaejoo.fitdo.domain.auth.service.application.req.LoginCreateCommand;
import com.jaejoo.fitdo.domain.auth.service.application.res.LoginResult;
import com.jaejoo.fitdo.domain.auth.web.req.LoginRequest;
import com.jaejoo.fitdo.domain.common.HeaderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResult> login(@RequestHeader("Authorization") String accessToken,
                                             @RequestBody LoginRequest loginRequest) {
        LoginResult login = authService.login(LoginCreateCommand.of(HeaderUtil.parseBearer(accessToken), loginRequest.getLoginType()));
        return new ResponseEntity<>(login, HttpStatusCode.valueOf(200));
    }
}
