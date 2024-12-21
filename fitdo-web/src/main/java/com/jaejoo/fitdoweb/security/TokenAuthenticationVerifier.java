package com.jaejoo.fitdoweb.security;

import com.jaejoo.fitdocore.auth.jwt.AuthToken;
import com.jaejoo.fitdoutil.exception.auth.TokenValidFailedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collection;
import java.util.Collections;

@Component
public class TokenAuthenticationVerifier {
    @Value("${app.auth.secret}")
    private String secret;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
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
