package com.jaejoo.fitdoweb.auth.web.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LoginRequest {
    //이정도의 중복은...허용하자!
    private String loginType;
}
