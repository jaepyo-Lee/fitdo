package com.jaejoo.fitdo.domain.auth.web.req;

import com.jaejoo.fitdo.domain.auth.service.application.req.AuthType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LoginRequest {
    private AuthType loginType;

}
