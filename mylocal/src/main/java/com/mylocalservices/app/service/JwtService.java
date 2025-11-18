package com.mylocalservices.app.service;

import com.mylocalservices.app.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    private final Key signingKey;
    private final long accessTokenValiditySec;
    private final long refreshTokenValiditySec;

    public JwtService(
            @Value("${app.jwt.secret}") String secretBase64,
            @Value("${app.jwt.access-token-validity-sec}") long accessSec,
            @Value("${app.jwt.refresh-token-validity-sec}") long refreshSec
    ) {
        this.signingKey = Keys.hmacShaKeyFor(secretBase64.getBytes());
        this.accessTokenValiditySec = accessSec;
        this.refreshTokenValiditySec = refreshSec;
    }

    public String generateAccessToken(User user) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(user.getPhone())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(accessTokenValiditySec)))
                .claim("role", user.getRole().name())
                .claim("userId", user.getId())
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(User user) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(user.getPhone())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(refreshTokenValiditySec)))
                .claim("userId", user.getId())
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public Claims parseClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token).getBody();
    }

    public String extractPhone(String token) {
        return parseClaims(token).getSubject();
    }
}
