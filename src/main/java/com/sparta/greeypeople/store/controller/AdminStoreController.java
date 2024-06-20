package com.sparta.greeypeople.store.controller;

import com.sparta.greeypeople.common.DataCommonResponse;
import com.sparta.greeypeople.store.dto.request.AdminStoreSaveRequestDto;
import com.sparta.greeypeople.store.dto.response.AdminStoreResponseDto;
import com.sparta.greeypeople.store.service.AdminStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
