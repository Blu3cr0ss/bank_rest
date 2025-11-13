package com.example.bankcards.service;

import com.example.bankcards.dto.UserJwtDto;
import com.example.bankcards.entity.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JwtService {
    private final SecretKey key;
    private final JwtParser parser;
    private final JwtBuilder builder;

    public JwtService(@Value("${jwt-salt}") String salt) {
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(salt));
        parser = Jwts.parser().verifyWith(key).build();
        builder = Jwts.builder();
    }

    public String generate(long userId, String username, Role role) {
        return builder
                .subject(String.valueOf(userId))
                .claim("user", username)
                .claim("role", role)
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    public UserJwtDto verify(String jwt) throws JwtException {
        Claims body = parser.parseSignedClaims(jwt).getPayload();
        UserJwtDto dto = new UserJwtDto();
        dto.id = Long.parseLong(body.getSubject());
        dto.username = body.get("user", String.class);
        dto.role = Role.valueOf(body.get("role", String.class));
        return dto;
    }
}
