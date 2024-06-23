package com.sparta.greeypeople.menu.controller;

import com.sparta.greeypeople.common.DataCommonResponse;
import com.sparta.greeypeople.common.StatusCommonResponse;
import com.sparta.greeypeople.menu.dto.request.AdminMenuSaveRequestDto;
import com.sparta.greeypeople.menu.dto.request.AdminMenuUpdateRequestDto;
import com.sparta.greeypeople.menu.dto.response.AdminMenuResponseDto;
import com.sparta.greeypeople.menu.service.AdminMenuService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/stores/{storeId}")
@RequiredArgsConstructor
public class AdminMenuController {

    private final AdminMenuService adminMenuService;

    /**
     * 가게 메뉴 등록 기능 ( 인가 필요 )
     *
     * @param storeId    : 메뉴를 등록 할 가게의 Id
     * @param requestDto : 등록할 메뉴의 정보
     * @return : 등록 된 가게 메뉴의 정보
     */
    @PostMapping("/menu")
    public ResponseEntity<DataCommonResponse<AdminMenuResponseDto>> postMenu(
        @Min(1) @PathVariable Long storeId,
        @Valid @RequestBody AdminMenuSaveRequestDto requestDto
    ) {
        AdminMenuResponseDto responseDto = adminMenuService.createMenu(storeId, requestDto);
        DataCommonResponse<AdminMenuResponseDto> response = new DataCommonResponse<>(201,
            "메뉴 등록 성공", responseDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * 가게 메뉴 수정 기능 ( 인가 필요 )
     *
     * @param storeId    : 메뉴를 수정 할 가게의 Id
     * @param menuId     : 수정할 메뉴의 Id
     * @param requestDto : 등록할 메뉴의 정보
     * @return : 수정 된 가게 메뉴의 정보
     */
    @PutMapping("/menu/{menuId}")
    public ResponseEntity<DataCommonResponse<AdminMenuResponseDto>> updateMenu(
        @Min(1) @PathVariable Long storeId,
        @Min(1) @PathVariable Long menuId,
        @Valid @RequestBody AdminMenuUpdateRequestDto requestDto
    ) {
        AdminMenuResponseDto responseDto = adminMenuService.updateMenu(storeId, menuId, requestDto);
        DataCommonResponse<AdminMenuResponseDto> response = new DataCommonResponse<>(201,
            "메뉴 등록 성공", responseDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 가게 메뉴 삭제 기능 ( 인가 필요 )
     *
     * @param storeId : 메뉴를 삭제 할 가게의 Id
     * @param menuId  : 삭제할 메뉴의 Id
     * @return : 삭제 완료 메시지 상태 코드 반환
     */
    @DeleteMapping("/menu/{menuId}")
    public ResponseEntity<StatusCommonResponse> deleteMenu(
        @Min(1) @PathVariable Long storeId,
        @Min(1) @PathVariable Long menuId
    ) {
        adminMenuService.deleteMenu(storeId, menuId);
        StatusCommonResponse response = new StatusCommonResponse(204,
            "메뉴 삭제 성공");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
