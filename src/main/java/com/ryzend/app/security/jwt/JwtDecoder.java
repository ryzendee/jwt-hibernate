package com.ryzend.app.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtDecoder {

    private final Key key;
    private final long expirationTimeMs;

    public JwtDecoder(@Value("${jwt.secret}") String secret,
                       @Value("${jwt.expiration-time}") long expirationTime) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationTimeMs = expirationTime;
    }

    public Jwt createToken(UserDetails userDetails) {
        Date issued = new Date();
        Date expiration = new Date(issued.getTime() + expirationTimeMs);

        String token =  Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issued)
                .setExpiration(expiration)
                .signWith(key)
                .compact();

        return new Jwt(userDetails.getUsername(), token, issued, expiration);
    }

    public Jwt parseToken(String token) throws JwtException {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return new Jwt(
                claims.getSubject(),
                token,
                claims.getIssuedAt(),
                claims.getExpiration()
        );
    }
}
