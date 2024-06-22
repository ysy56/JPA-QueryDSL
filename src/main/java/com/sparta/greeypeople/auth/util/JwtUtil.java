package com.sparta.greeypeople.auth.util;

import com.sparta.greeypeople.auth.config.JwtConfig;
import com.sparta.greeypeople.exception.TokenExpiredException;
import com.sparta.greeypeople.user.enumeration.UserAuth;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import org.springframework.util.StringUtils;

/**
 * JwtUtil: JWT 토큰을 생성하고 검증하는 유틸리티 클래스
 */
@Component
public class JwtUtil {

    @Value("${jwt.token.expiration}")
    private long ACCESS_TOKEN_TIME;

    @Value("${jwt.refresh.token.expiration}")
    private long REFRESH_TOKEN_TIME;

    public static final String BEARER_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_KEY = "auth";
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    private final JwtConfig jwtConfig;
    private final Key key;

    public JwtUtil(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
        this.key = jwtConfig.getKey();
    }

    public String generateToken(String userId, String userName, UserAuth userAuth, long tokenTime) {

        Date date = new Date();

        return Jwts.builder()
            .setSubject(userId)
            .claim("userName", userName)
            .claim(AUTHORIZATION_KEY, userAuth.toString())
            .setExpiration(new Date(date.getTime() + tokenTime))
            .setIssuedAt(date)
            .signWith(key, signatureAlgorithm)
            .compact();
    }

    public String generateAccessToken(String userId, String userName, UserAuth userAuth) {
        return generateToken(userId, userName, userAuth, ACCESS_TOKEN_TIME);
    }

    public String generateRefreshToken(String userId, String userName, UserAuth userAuth) {
        return generateToken(userId, userName, userAuth, REFRESH_TOKEN_TIME);
    }

    public String generateNewRefreshToken(String userId, String userName, UserAuth userAuth, Date expirationDate) {

        Date date = new Date();

        return Jwts.builder()
            .setSubject(userId)
            .claim("userName", userName)
            .claim(AUTHORIZATION_KEY, userAuth.toString())
            .setExpiration(expirationDate)
            .setIssuedAt(date)
            .signWith(key, signatureAlgorithm)
            .compact();
    }

    public ResponseCookie generateRefreshTokenCookie(String refreshToken) {
        return ResponseCookie.from("refreshToken", refreshToken)
            .httpOnly(true)
            .maxAge(REFRESH_TOKEN_TIME / 1000)
            .path("/")
            .sameSite("Strict")
            .build();
    }

    public ResponseCookie generateNewRefreshTokenCookie(String refreshToken, Date expirationDate) {

        long maxAge = (expirationDate.getTime() - new Date().getTime()) / 1000;

        return ResponseCookie.from("refreshToken", refreshToken)
            .httpOnly(true)
            .maxAge(maxAge)
            .path("/")
            .sameSite("Strict")
            .build();
    }

    public String getAccessTokenFromHeader(HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");

        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith(BEARER_PREFIX)) {
            return authorizationHeader.substring(7);
        }

        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void checkTokenExpiration(String token) throws TokenExpiredException {

        try {

            Claims claims = getClaimsFromToken(token);
            Date date = claims.getExpiration();
            Date now = new Date();

            if (date != null && date.before(now)) {
                throw new TokenExpiredException("토큰이 만료되었습니다.");
            }

        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException("토큰이 만료되었습니다.");
        }

    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}
