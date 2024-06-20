package com.sparta.greeypeople.auth.controller;

import com.sparta.greeypeople.auth.dto.RefreshTokenRequestDto;
import com.sparta.greeypeople.auth.dto.SignupRequestDto;
import com.sparta.greeypeople.auth.dto.TokenResponseDto;
import com.sparta.greeypeople.auth.entity.LoginRequest;
import com.sparta.greeypeople.auth.entity.LoginResponse;
import com.sparta.greeypeople.auth.service.UserService;
import com.sparta.greeypeople.auth.util.JwtUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class AuthRestController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthRestController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        TokenResponseDto tokens = userService.login(loginRequest);
        String accessToken = tokens.getAccessToken();
        String refreshToken = tokens.getRefreshToken();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Refresh-Token", refreshToken);

        return new ResponseEntity<>("로그인 성공", headers, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestParam String username) {
        userService.logout(username);
        return ResponseEntity.ok("로그아웃 성공");
    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestParam String username, @RequestParam String password) {
        userService.withdraw(username, password);
        return ResponseEntity.ok("회원탈퇴 성공");
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponseDto> refresh(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        String refreshToken = refreshTokenRequestDto.getRefreshToken();
        String newAccessToken = jwtUtil.refreshToken(refreshToken);
        TokenResponseDto tokenResponseDto = new TokenResponseDto(newAccessToken, refreshToken);
        return ResponseEntity.ok(tokenResponseDto);
    }
}
