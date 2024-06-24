package com.sparta.greeypeople.user.controller;

import com.sparta.greeypeople.common.DataCommonResponse;
import com.sparta.greeypeople.common.StatusCommonResponse;
import com.sparta.greeypeople.user.dto.request.AdminUserAuthRequestDto;
import com.sparta.greeypeople.user.dto.request.AdminUserProfileRequestDto;
import com.sparta.greeypeople.user.dto.request.BlockUserRequestDto;
import com.sparta.greeypeople.user.dto.response.AdminUserResponseDto;
import com.sparta.greeypeople.user.service.AdminUserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<DataCommonResponse<List<AdminUserResponseDto>>> getAllUser() {
        List<AdminUserResponseDto> responseDto = adminUserService.findAllUser();
        DataCommonResponse<List<AdminUserResponseDto>> response = new DataCommonResponse<>(200,
            "전체 회원 조회 성공", responseDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 특정 회원 프로필 수정 ( 인가 필요 )
     *
     * @param userId : 수정할 회원의 Id
     * @return : 등록 된 특정 회원 정보
     */
    @PutMapping("/{userId}/profile")
    public ResponseEntity<DataCommonResponse<AdminUserResponseDto>> updateUserProfile(
        @Min(1) @PathVariable Long userId,
        @Valid @RequestBody AdminUserProfileRequestDto requestDto
    ) {
        AdminUserResponseDto responseDto = adminUserService.updateUserProfile(userId, requestDto);
        DataCommonResponse<AdminUserResponseDto> response = new DataCommonResponse<>(200,
            "특정 회원 프로필 수정 성공", responseDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 특정 회원 삭제 ( 인가 필요 )
     *
     * @param userId : 수정할 회원의 Id
     * @return : 삭제 완료 메시지 상태 코드 반환
     */
    @DeleteMapping("/{userId}") // @AuthenticationPrincipal UserDetails
    public ResponseEntity<StatusCommonResponse> deleteUser(
        @Min(1) @PathVariable Long userId
    ) {
        adminUserService.deleteUser(userId);
        StatusCommonResponse response = new StatusCommonResponse(204, "회원 삭제 성공");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 회원 권한 변경 ( 인가 필요 )
     *
     * @param userId : 권한을 변경할 회원의 Id
     * @return : 권한 변경 완료 메시지 상태 코드 반환
     */
    @PutMapping("/{userId}/auth") // @AuthenticationPrincipal UserDetails
    public ResponseEntity<StatusCommonResponse> updateUserAuth(
        @Min(1) @PathVariable Long userId,
        @Valid @RequestBody AdminUserAuthRequestDto requestDto
    ) {
        adminUserService.updateUserAuth(userId, requestDto);
        StatusCommonResponse response = new StatusCommonResponse(200, "회원 권한 변경 성공");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 특정 회원 차단 ( 인가 필요 )
     *
     * @param userId : 권한을 변경할 회원의 Id
     * @return : 차단 완료 메시지 상태 코드 반환
     */
    @PutMapping("/{userId}/block")
    public ResponseEntity<StatusCommonResponse> blockUser(
        @PathVariable Long userId,
        @RequestBody BlockUserRequestDto requestDto
    ) {
        adminUserService.blockUser(userId, requestDto.getReason());
        StatusCommonResponse response = new StatusCommonResponse(200, "회원 차단 성공");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
