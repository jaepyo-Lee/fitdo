package com.jaejoo.fitdo.global.exception.auth;

import com.jaejoo.fitdo.global.exception.ApplicationRunException;
import com.jaejoo.fitdo.global.exception.ErrorEnumCode;
import lombok.Getter;

@Getter
public class ExpiredJwtTokenException extends ApplicationRunException {
    private static final ErrorEnumCode CODE = JwtErrorCode.EXPIRED_JWT_TOKEN_ERROR;

    public ExpiredJwtTokenException(){
        this(CODE);
    }
    private ExpiredJwtTokenException(ErrorEnumCode errorEnumCode) {
        super(errorEnumCode);
    }
}
