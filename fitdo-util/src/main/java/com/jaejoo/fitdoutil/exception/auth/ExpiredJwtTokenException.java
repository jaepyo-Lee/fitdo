package com.jaejoo.fitdoutil.exception.auth;

import com.jaejoo.fitdoutil.exception.ApplicationRunException;
import com.jaejoo.fitdoutil.exception.ErrorEnumCode;
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
