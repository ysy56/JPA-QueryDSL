package com.sparta.greeypeople.store.controller;

import com.sparta.greeypeople.common.DataCommonResponse;
import com.sparta.greeypeople.store.dto.request.AdminStoreSaveRequestDto;
import com.sparta.greeypeople.store.dto.request.AdminStoreUpdateRequestDto;
import com.sparta.greeypeople.store.dto.response.AdminStoreResponseDto;
import com.sparta.greeypeople.store.service.AdminStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminStoreController {

    private final AdminStoreService adminStoreService;

    /**
     * 가게 등록 기능 ( 인가 필요 )
     * @param requestDto : 등록할 가게의 정보
     * @return : 등록 된 가게의 정보
     */
    @PostMapping("/store") // @AuthenticationPrincipal UserDetails
    public ResponseEntity<DataCommonResponse<AdminStoreResponseDto>> saveStore(
            @RequestBody AdminStoreSaveRequestDto requestDto
    ) {
        AdminStoreResponseDto responseDto = adminStoreService.saveStore(requestDto);
        DataCommonResponse<AdminStoreResponseDto> response = new DataCommonResponse<>(201, "가게 등록 성공", responseDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * 가게 수정 기능 ( 인가 필요 )
     * @param requestDto : 수정할 가게의 정보
     * @return : 수정 된 가게의 정보
     */
    @PutMapping("/stores/{storeId}") // @AuthenticationPrincipal UserDetails
    public ResponseEntity<DataCommonResponse<AdminStoreResponseDto>> updateStore(
            @PathVariable Long storeId,
            @RequestBody AdminStoreUpdateRequestDto requestDto
    ) {
        AdminStoreResponseDto responseDto = adminStoreService.updateStore(storeId, requestDto);
        DataCommonResponse<AdminStoreResponseDto> response = new DataCommonResponse<>(200, "가게 수정 성공", responseDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
