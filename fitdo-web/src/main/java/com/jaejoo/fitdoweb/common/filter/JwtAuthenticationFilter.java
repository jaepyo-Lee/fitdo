package com.jaejoo.fitdoweb.common.filter;

import com.jaejoo.fitdocore.auth.jwt.AuthToken;
import com.jaejoo.fitdoweb.common.util.HeaderUtil;
import com.jaejoo.fitdoweb.security.TokenAuthenticationVerifier;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final static String AUTHORIZATION_HEADER = "Authorization";
    private final TokenAuthenticationVerifier verifier;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        String parseToken = HeaderUtil.parseBearer(req.getHeader(AUTHORIZATION_HEADER));
        if (parseToken != null) {
            AuthToken authToken = verifier.convertAuthToken(parseToken);
            if (authToken.validate()) {
                SecurityContextHolder.getContext().setAuthentication(verifier.getAuthentication(authToken));
            }
        }
        filterChain.doFilter(req, res);
    }
}
