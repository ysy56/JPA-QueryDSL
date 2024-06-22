package com.sparta.greeypeople.user.controller;

import com.sparta.greeypeople.user.dto.request.PasswordRequestDto;
import com.sparta.greeypeople.user.dto.request.SignupRequestDto;
import com.sparta.greeypeople.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.greeypeople.common.StatusCommonResponse;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<StatusCommonResponse> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        userService.signup(requestDto);

        StatusCommonResponse commonResponse = new StatusCommonResponse(201, "회원가입 성공");

        return new ResponseEntity<>(commonResponse, HttpStatus.CREATED);
    }

    @PostMapping("/logout")
    public ResponseEntity<StatusCommonResponse> logout() {
        userService.logout();

        StatusCommonResponse commonResponse = new StatusCommonResponse(200, "로그아웃 성공");

        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    @PutMapping("/withdrawal")
    public ResponseEntity<StatusCommonResponse> withdrawal(@Valid @RequestBody PasswordRequestDto requestDto) {
        userService.withdrawal(requestDto);

        StatusCommonResponse commonResponse = new StatusCommonResponse(200, "회원탈퇴 성공");

        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<StatusCommonResponse> refresh(HttpServletRequest request) {
        HttpHeaders headers = userService.refresh(request);

        StatusCommonResponse commonResponse = new StatusCommonResponse(200, "RefreshToken 인증 성공");

        return new ResponseEntity<>(commonResponse, headers, HttpStatus.OK);
    }
}