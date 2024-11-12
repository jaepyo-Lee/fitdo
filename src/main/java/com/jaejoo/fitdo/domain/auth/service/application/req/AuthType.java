package com.jaejoo.fitdo.domain.auth.service.application.req;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AuthType {
    KAKAO("https://kapi.kakao.com/v2/user/me"),
    NOT_APPLY("not apply"),;
    private String serverUri;
}
