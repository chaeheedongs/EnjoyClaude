package com.enjoy.EnjoyClaude.infrastructures.security;

import com.enjoy.EnjoyClaude.domains.common.exception.ExpiredTokenException;
import com.enjoy.EnjoyClaude.domains.common.exception.InvalidTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {
    private final JwtProperties jwtProperties;
    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(final String email, final Long userId) {
        return generateToken(email, userId, jwtProperties.getAccessTokenValidity());
    }

    public String generateRefreshToken(final String email, final Long userId) {
        return generateToken(email, userId, jwtProperties.getRefreshTokenValidity());
    }

    private String generateToken(final String email, final Long userId, final long validityInSeconds) {
        final Date now = new Date();
        final Date expiryDate = new Date(now.getTime() + validityInSeconds * 1000);

        return Jwts.builder()
                .setSubject(email)
                .claim("userId", userId)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(final String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.warn("만료된 JWT 토큰입니다.");
            throw new ExpiredTokenException("토큰이 만료되었습니다.");
        } catch (UnsupportedJwtException e) {
            log.warn("지원하지 않는 JWT 토큰입니다.");
            throw new InvalidTokenException("지원하지 않는 토큰입니다.");
        } catch (MalformedJwtException e) {
            log.warn("잘못된 JWT 토큰입니다.");
            throw new InvalidTokenException("잘못된 토큰입니다.");
        } catch (SignatureException e) {
            log.warn("JWT 서명이 잘못되었습니다.");
            throw new InvalidTokenException("서명이 잘못된 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.warn("JWT 토큰이 비어있습니다.");
            throw new InvalidTokenException("토큰이 비어있습니다.");
        }
    }

    public String getEmailFromToken(final String token) {
        final Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public Long getUserIdFromToken(final String token) {
        final Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("userId", Long.class);
    }
}
