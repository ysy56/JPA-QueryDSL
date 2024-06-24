package com.sparta.greeypeople.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.greeypeople.common.StatusCommonResponse;
import com.sparta.greeypeople.user.service.KakaoService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class KakaoController {
    private final KakaoService kakaoService;


    @GetMapping("/login/kakao")
    public ResponseEntity<StatusCommonResponse> kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        kakaoService.kakaoLogin(code, response);  // JWT 반환

        // 클라이언트에게 성공적인 로그인 메시지 반환
        StatusCommonResponse commonResponse = new StatusCommonResponse(200, "카카오 로그인 성공");
        return ResponseEntity.ok().body(commonResponse);
    }

}