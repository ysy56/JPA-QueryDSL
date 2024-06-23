package com.sparta.greeypeople.user.controller;

import com.sparta.greeypeople.auth.security.UserDetailsImpl;
import com.sparta.greeypeople.common.DataCommonResponse;
import com.sparta.greeypeople.common.StatusCommonResponse;
import com.sparta.greeypeople.user.dto.request.AdminUserAuthRequestDto;
import com.sparta.greeypeople.user.dto.request.AdminUserProfileRequestDto;
import com.sparta.greeypeople.user.dto.response.AdminUserResponseDto;
import com.sparta.greeypeople.user.service.AdminUserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class AdminUserController {

    private final AdminUserService adminUserService;

    /**
     * 전체 회원 조회 ( 인가 필요 )
     *
     * @return : 등록 된 전체 회원 정보
     */
    @GetMapping // @AuthenticationPrincipal UserDetails
    public ResponseEntity<DataCommonResponse<List<AdminUserResponseDto>>> selectAllUser() {
        List<AdminUserResponseDto> responseDto = adminUserService.getAllUsers();
        DataCommonResponse<List<AdminUserResponseDto>> response = new DataCommonResponse<>(200,
            "전체 회원 조회 성공", responseDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 특정 회원 프로필 수정 ( 인가 필요 )
     *
     * @return : 등록 된 특정 회원 정보
     */
    @PutMapping("/{userId}/profile") // @AuthenticationPrincipal UserDetails
    public ResponseEntity<DataCommonResponse<AdminUserResponseDto>> updateUserProfile(
        @PathVariable Long userId,
        @RequestBody AdminUserProfileRequestDto requestDto
    ) {
        AdminUserResponseDto responseDto = adminUserService.updateUserProfile(userId, requestDto);
        DataCommonResponse<AdminUserResponseDto> response = new DataCommonResponse<>(200,
            "특정 회원 프로필 수정 성공", responseDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 특정 회원 삭제 ( 인가 필요 )
     *
     * @return : 삭제 완료 메시지 상태 코드 반환
     */
    @DeleteMapping("/{userId}") // @AuthenticationPrincipal UserDetails
    public ResponseEntity<StatusCommonResponse> deleteUser(
        @PathVariable Long userId
    ) {
        adminUserService.deleteUser(userId);
        StatusCommonResponse response = new StatusCommonResponse(204, "회원 삭제 성공");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 회원 권한 변경 ( 인가 필요 )
     *
     * @return : 권한 변경 완료 메시지 상태 코드 반환
     */
    @PutMapping("/{userId}/auth") // @AuthenticationPrincipal UserDetails
    public ResponseEntity<StatusCommonResponse> updateUserAuth(
        @PathVariable Long userId,
        @RequestBody AdminUserAuthRequestDto requestDto
    ) {
        adminUserService.updateUserAuth(userId, requestDto);
        StatusCommonResponse response = new StatusCommonResponse(200, "회원 권한 변경 성공");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
