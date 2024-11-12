package com.jaejoo.fitdo.domain.auth.service.domain;

import com.jaejoo.fitdo.global.exception.auth.ExpiredJwtTokenException;
import io.jsonwebtoken.*;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;

@Slf4j
public record AuthToken(String token, Key key) {

    public boolean validate() {
        return getTokenClaims() != null;
    }

    public Claims getTokenClaims() {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SecurityException e) {
            log.info("Invalid JWT signature.");
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            throw new ExpiredJwtTokenException();
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
        }
        return null;
    }
}
