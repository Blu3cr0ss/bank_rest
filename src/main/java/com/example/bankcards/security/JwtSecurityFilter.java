package com.example.bankcards.security;

import com.example.bankcards.dto.UserJwtDto;
import com.example.bankcards.exception.ApiException;
import com.example.bankcards.service.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Objects;

@Component
public class JwtSecurityFilter extends OncePerRequestFilter {
    @Autowired
    JwtService jwtService;
    @Autowired
    HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String header = request.getHeader("Authorization");
            if (header == null) {
                throw new ApiException("No Authorization header", HttpStatus.UNAUTHORIZED);
            }
            if (!header.startsWith("Bearer ")) {
                throw new ApiException("Invalid Bearer header", HttpStatus.UNAUTHORIZED);
            }
            String jwt = header.substring(7);
            UserJwtDto user = jwtService.verify(jwt);

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            AbstractAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    user, null, user.getAuthorities()
            );
            context.setAuthentication(token);
            SecurityContextHolder.setContext(context);
            filterChain.doFilter(request, response);
        } catch (ApiException | JwtException e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path;
        if (Objects.equals(request.getServletPath(), "")) path = request.getPathInfo();
        else path = request.getServletPath();
        return path.startsWith("/api/auth/") ||
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/swagger-ui");
    }
}
