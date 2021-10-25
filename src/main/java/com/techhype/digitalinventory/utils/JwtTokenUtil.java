package com.techhype.digitalinventory.utils;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Optional;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techhype.digitalinventory.models.TokenData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = -2550185165626007488L;

    public static final long JWT_TOKEN_VALIDITY = (long) (60 * 24 * 60 * 1000);

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(TokenData tokenData) throws IOException {
        return Jwts.builder().setSubject(new ObjectMapper().writeValueAsString(tokenData))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes())).compact();
    }

    public Optional<TokenData> decodeToken(String token) throws IOException {
        try {
            var json = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes())).build()
                    .parseClaimsJws(token).getBody().getSubject();
            return Optional.of(new ObjectMapper().readValue(json, TokenData.class));
        } catch (JwtException e) {
            return Optional.empty();
        }
    }
}
