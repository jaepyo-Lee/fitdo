package com.jaejoo.fitdoweb.auth.web;

import com.jaejoo.fitdocore.auth.AuthService;
import com.jaejoo.fitdocore.auth.req.LoginCreateCommand;
import com.jaejoo.fitdocore.auth.res.LoginResult;
import com.jaejoo.fitdoweb.auth.web.req.LoginRequest;
import com.jaejoo.fitdoweb.common.util.HeaderUtil;
import com.jaejoo.fitdoweb.security.CustomUserDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResult> login(@RequestHeader("Authorization") String accessToken,
                                             @RequestBody LoginRequest loginRequest,
                                             ServletWebRequest servletWebRequest) {
        
        LoginResult login = authService.login(LoginCreateCommand.of(HeaderUtil.parseBearer(accessToken), loginRequest.getLoginType()));
        return new ResponseEntity<>(login, HttpStatusCode.valueOf(200));
    }

    @DeleteMapping("/auth/out")
    public ResponseEntity<Void> signout(@AuthenticationPrincipal CustomUserDetail userDetail) {
        authService.out(userDetail.userId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
