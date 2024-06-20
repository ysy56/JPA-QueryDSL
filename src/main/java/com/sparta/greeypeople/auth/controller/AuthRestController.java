package com.sparta.greeypeople.auth.controller;

import com.sparta.greeypeople.auth.dto.RefreshTokenRequestDto;
import com.sparta.greeypeople.auth.dto.SignupRequestDto;
import com.sparta.greeypeople.auth.dto.PasswordRequest;
import com.sparta.greeypeople.auth.dto.TokenResponseDto;
import com.sparta.greeypeople.auth.entity.LoginRequest;
import com.sparta.greeypeople.auth.service.UserService;
import com.sparta.greeypeople.auth.util.JwtUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * 인증기능 컨트롤러
 * - 회원가입
 * - 로그인
 * - 로그아웃
 * - 회원탈퇴
 * - 리프레시 토큰 재발급
 */
@RestController
@RequestMapping("/users")
public class AuthRestController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    /**
     * 생성자
     * @param userService 사용자 서비스
     * @param jwtUtil JWT 유틸리티
     */
    public AuthRestController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * 회원가입
     * @param signupRequestDto 회원가입 요청 DTO
     * @return 회원가입 성공 메시지와 상태 코드
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공");
    }

    /**
     * 로그인
     * @param loginRequest 로그인 요청 DTO
     * @return 로그인 성공 메시지와 토큰 헤더
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest loginRequest) {
        TokenResponseDto tokens = userService.login(loginRequest);
        String accessToken = tokens.getAccessToken();
        String refreshToken = tokens.getRefreshToken();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Refresh-Token", refreshToken);

        return new ResponseEntity<>("로그인 성공", headers, HttpStatus.OK);
    }

    /**
     * 로그아웃
     * @param token 인증 토큰
     * @return 로그아웃 성공 메시지
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        String userId = jwtUtil.getUsernameFromToken(token.substring(7));
        userService.logout(userId);
        return ResponseEntity.ok("로그아웃 성공");
    }

    /**
     * 회원탈퇴
     * @param token 인증 토큰
     * @param passwordRequest 비밀번호 요청 DTO
     * @return 회원탈퇴 성공 메시지
     */
    @PutMapping("/withdrawal")
    public ResponseEntity<String> withdraw(@RequestHeader("Authorization") String token, @Valid @RequestBody PasswordRequest passwordRequest) {
        String userId = jwtUtil.getUsernameFromToken(token.substring(7));
        userService.withdraw(userId, passwordRequest.getPassword());
        return ResponseEntity.ok("회원탈퇴 성공");
    }

    /**
     * 리프레시 토큰 재발급
     * @param refreshTokenRequestDto 리프레시 토큰 요청 DTO
     * @return 새로운 액세스 토큰과 리프레시 토큰
     */
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponseDto> refresh(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        String refreshToken = refreshTokenRequestDto.getRefreshToken();
        String newAccessToken = jwtUtil.refreshToken(refreshToken);
        TokenResponseDto tokenResponseDto = new TokenResponseDto(newAccessToken, refreshToken);
        return ResponseEntity.ok(tokenResponseDto);
    }
}
