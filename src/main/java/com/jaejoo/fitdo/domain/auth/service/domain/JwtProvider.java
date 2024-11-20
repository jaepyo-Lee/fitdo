package com.jaejoo.fitdo.domain.auth.service.domain;

import com.jaejoo.fitdo.domain.auth.service.application.req.AuthType;
import com.jaejoo.fitdo.domain.auth.service.domain.dto.TokenSet;
import com.jaejoo.fitdo.domain.user.core.Account;
import com.jaejoo.fitdo.domain.user.core.GrantRole;
import com.jaejoo.fitdo.global.format.exception.auth.TokenValidFailedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;

@NoArgsConstructor
@Component
public class JwtProvider {
    @Value("${app.auth.accessExpired}")
    private long ACCESS_TOKEN_VALIDATION_MILLISECOND;

    @Value("${app.auth.refreshExpired}")
    private long REFRESH_TOKEN_VALIDATION_MILLISECOND;

    @Value("${app.auth.secret}")
    private String secret;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public JwtProvider(Key key, long REFRESH_TOKEN_VALIDATION_MILLISECOND, long ACCESS_TOKEN_VALIDATION_MILLISECOND) {
        this.key = key;
        this.REFRESH_TOKEN_VALIDATION_MILLISECOND = REFRESH_TOKEN_VALIDATION_MILLISECOND;
        this.ACCESS_TOKEN_VALIDATION_MILLISECOND = ACCESS_TOKEN_VALIDATION_MILLISECOND;
    }

    public TokenSet createTokenSet(Account account, AuthType loginType) {
        String accessJwt = createJwt(account.getUserId(), account.getRole(), ACCESS_TOKEN_VALIDATION_MILLISECOND, loginType.toString());
        String refreshJwt = createJwt(account.getUserId(), account.getRole(), REFRESH_TOKEN_VALIDATION_MILLISECOND, loginType.toString());
        return TokenSet.ofBearer(accessJwt, refreshJwt);
    }

    private String createJwt(Long userId, GrantRole role, long durationMilliSeconds, String loginType) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + durationMilliSeconds); // Ensure duration is in milliseconds
        return Jwts.builder()
                .setClaims(createClaimByAuthId(String.valueOf(userId), loginType, role))
                .setSubject("fitdo") // Can be replaced with a configurable subject if needed
                .setExpiration(expiration)
                .setIssuedAt(now)
                .signWith(key)
                .compact();
    }

    private Map<String, Object> createClaimByAuthId(String userId, String loginType, GrantRole role) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("loginType", loginType);
        map.put("role", role);
        return map;
    }

    public AuthToken convertAuthToken(String token) {
        return new AuthToken(token, key);
    }

    public Long getTokenExpiration(String token) {
        try {
            Claims body = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return body.getExpiration().getTime();
        } catch (JwtException e) {
            throw new TokenValidFailedException();
        }
    }

    public Authentication getAuthentication(AuthToken authToken) {
        if (!authToken.validate()) {
            throw new TokenValidFailedException();
        }

        Claims tokenClaims = authToken.getTokenClaims();
        String role = tokenClaims.get("role", String.class);
        String userId = tokenClaims.get("userId", String.class);

        if (role == null || userId == null) {
            throw new IllegalArgumentException("Token claims are missing required information.");
        }

        Collection<? extends GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));
        CustomUserDetail principal = new CustomUserDetail(Long.parseLong(userId));

        return new UsernamePasswordAuthenticationToken(principal, null, authorities);
    }
}
