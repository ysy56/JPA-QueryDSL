package com.sparta.greeypeople.user.controller;

import com.sparta.greeypeople.common.DataCommonResponse;
import com.sparta.greeypeople.user.dto.AdminUserResponseDto;
import com.sparta.greeypeople.user.service.AdminUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/users")
public class AdminUserController {

    private final AdminUserService adminUserService;

    public AdminUserController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    /**
     * 전체 회원 조회 ( 인가 필요 )
     * @return : 등록 된 전체 회원 정보
     */
    @GetMapping // @AuthenticationPrincipal UserDetails
    public ResponseEntity<DataCommonResponse<AdminUserResponseDto>> selectAllUser() {
        AdminUserResponseDto responseDto = adminUserService.getAllUsers();
        DataCommonResponse<AdminUserResponseDto> response = new DataCommonResponse<>(200, "전체 회원 조회 성공", responseDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
