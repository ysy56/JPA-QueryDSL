package com.sparta.greeypeople.menu.controller;

import com.sparta.greeypeople.common.DataCommonResponse;
import com.sparta.greeypeople.menu.dto.request.AdminMenuSaveRequestDto;
import com.sparta.greeypeople.menu.dto.response.AdminMenuResponseDto;
import com.sparta.greeypeople.menu.service.AdminMenuService;
import com.sparta.greeypeople.user.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/stores/{storeId}")
@RequiredArgsConstructor
public class AdminMenuController {

    private AdminMenuService adminMenuService;

    /**
     * 가게 메뉴 등록 기능 ( 인가 필요 )
     *
     * @param storeId    : 메뉴를 등록 할 가게의 Id
     * @param requestDto : 등록할 메뉴의 정보
     * @return : 등록 된 가게 메뉴의 정보
     */
    @PostMapping("/menu") // @AuthenticationPrincipal UserDetails
    public ResponseEntity<DataCommonResponse<AdminMenuResponseDto>> saveMenu(
        @PathVariable Long storeId,
        @RequestBody AdminMenuSaveRequestDto requestDto
    ) {
        AdminMenuResponseDto responseDto = adminMenuService.saveMenu(storeId, requestDto);
        DataCommonResponse<AdminMenuResponseDto> response = new DataCommonResponse<>(201,
            "메뉴 등록 성공", responseDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
