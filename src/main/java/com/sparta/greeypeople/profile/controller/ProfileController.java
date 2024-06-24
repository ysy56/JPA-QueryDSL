package com.sparta.greeypeople.profile.controller;

import com.sparta.greeypeople.auth.security.UserDetailsImpl;
import com.sparta.greeypeople.profile.dto.request.PasswordUpdateRequestDto;
import com.sparta.greeypeople.profile.dto.request.ProfileRequestDto;
import com.sparta.greeypeople.profile.dto.response.ProfileResponseDto;
import com.sparta.greeypeople.profile.service.ProfileService;
import com.sparta.greeypeople.common.StatusCommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * ProfileController는 사용자 프로필 관련 API 요청을 처리
 * - GET /users/profile: 프로필 조회
 * - PUT /users/profile: 프로필 수정
 * - PUT /users/profile/password: 비밀번호 수정
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/profile")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<StatusCommonResponse<ProfileResponseDto>> getProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        ProfileResponseDto responseDto = profileService.getProfile(userDetails.getUser());
        return ResponseEntity.ok(new StatusCommonResponse<>(200, "마이페이지 조회 성공", responseDto));
    }

    @PutMapping
    public ResponseEntity<StatusCommonResponse<ProfileResponseDto>> updateProfile(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ProfileRequestDto requestDto) {
        ProfileResponseDto responseDto = profileService.updateProfile(userDetails.getUser(), requestDto);
        return ResponseEntity.ok(new StatusCommonResponse<>(200, "마이페이지 수정 성공", responseDto));
    }

    @PutMapping("/password")
    public ResponseEntity<StatusCommonResponse<String>> updatePassword(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PasswordUpdateRequestDto requestDto) {
        profileService.updatePassword(userDetails.getUser(), requestDto);
        return ResponseEntity.ok(new StatusCommonResponse<>(200, "비밀번호 수정 성공"));
    }
}
