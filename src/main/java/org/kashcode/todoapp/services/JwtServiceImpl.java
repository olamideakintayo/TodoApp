package org.kashcode.todoapp.services;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    private static final long DEFAULT_CLOCK_SKEW_SECONDS = 30L;

    private final Key key;
    private final long expirationTime;

    public JwtServiceImpl(
            @Value("${JWT_SECRET}") String secret,
            @Value("${JWT_EXPIRATION}") long expirationTime) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationTime = expirationTime;
    }

    @Override
    public String generateToken(String username) {
        Date currentDate = new Date();
        Date expiry = new Date(currentDate.getTime() + expirationTime);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String extractUsername(String token) {
        return parseClaims(normalize(token)).getSubject();
    }

    @Override
    public boolean validateToken(String token, String username) {
        try {
            Claims claims

                    = parseClaims(normalize(token));
            return username != null
                    && username.equals(claims.getSubject())
                    && claims.getExpiration().after(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .setAllowedClockSkewSeconds(DEFAULT_CLOCK_SKEW_SECONDS)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private String normalize(String token) {
        if (token == null) return "";
        String matchedToken = token.trim();
        if (matchedToken.regionMatches(true, 0, "Bearer ", 0, 7)) {
            return matchedToken.substring(7).trim();
        }
        return matchedToken;
    }
}