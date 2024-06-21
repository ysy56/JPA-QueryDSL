package com.sparta.greeypeople.auth.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

/**
 * JwtUtil: JWT 토큰을 생성하고 검증하는 유틸리티 클래스
 */
@Component
public class JwtUtil {
    private String secretKey;

    @Value("${jwt.secret.key}")
    public void setSecretKey(String secretKey) {
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    @Value("${jwt.token.expiration}")
    private long tokenExpiration;

    @Value("${jwt.refresh.token.expiration}")
    private long refreshTokenExpiration;

    // 액세스 토큰 생성
    public String createAccessToken(String userId) {
        return generateToken(userId, tokenExpiration);
    }

    // 리프레시 토큰 생성
    public String createRefreshToken(String userId) {
        return generateToken(userId, refreshTokenExpiration);
    }

    // JWT 토큰 생성
    private String generateToken(String userId, long expiration) {
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // JWT 토큰에서 정보 추출
    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    // 토큰 유효성 검증
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // 토큰 만료 여부 확인
    private boolean isTokenExpired(String token) {
        final Date expiration = extractClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    // 토큰으로부터 사용자 아이디 추출
    public String getUsernameFromToken(String token) {
        return extractClaims(token).getSubject();
    }

    // 리프레시 토큰으로 새 엑세스 토큰 재발급
    public String refreshToken(String refreshToken) {
        if (validateRefreshToken(refreshToken)) {
            String username = getUsernameFromToken(refreshToken);
            return createAccessToken(username);
        } else {
            throw new IllegalArgumentException("리프레시 토큰이 유효하지 않습니다.");
        }
    }

    // 리프레시 토큰의 유효성 검증
    public boolean validateRefreshToken(String token) {
        return !isTokenExpired(token);
    }
}
