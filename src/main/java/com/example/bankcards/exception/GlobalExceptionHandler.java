package com.example.bankcards.exception;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<String> handleAll(
            ApiException ex, HttpServletRequest servletRequest
    ) {
        return ResponseEntity.status(ex.getStatus())
                .body(ex.getMessage());
    }
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<String> handleAll(
            JwtException ex, HttpServletRequest servletRequest
    ) {
        return ResponseEntity.status(401)
                .body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAll(
            Exception ex, HttpServletRequest servletRequest
    ) {
        return ResponseEntity.status(500)
                .body(ex.getMessage());
    }
}
