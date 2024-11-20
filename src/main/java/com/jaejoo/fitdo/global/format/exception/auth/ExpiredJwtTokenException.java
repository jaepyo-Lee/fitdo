package com.jaejoo.fitdo.global.format.exception.auth;

import com.jaejoo.fitdo.global.format.exception.ApplicationRunException;
import com.jaejoo.fitdo.global.format.exception.ErrorEnumCode;
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
