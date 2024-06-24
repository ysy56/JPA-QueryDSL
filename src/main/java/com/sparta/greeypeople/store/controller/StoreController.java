package com.sparta.greeypeople.store.controller;

import com.sparta.greeypeople.common.DataCommonResponse;
import com.sparta.greeypeople.store.dto.response.AdminStoreResponseDto;
import com.sparta.greeypeople.store.service.AdminStoreService;
import com.sparta.greeypeople.store.service.StoreService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    /**
     * 가게 단건 조회 기능
     *
     * @param storeId    : 리뷰 등록된 가게의 Id
     * @return : 등록된 가게 정보
     */
    @GetMapping("/stores/{storeId}")
    public ResponseEntity<DataCommonResponse<AdminStoreResponseDto>> getStore(
        @PathVariable Long storeId
    ){
        AdminStoreResponseDto store = storeService.getStore(storeId);
        DataCommonResponse<AdminStoreResponseDto> response = new DataCommonResponse<>(200, "가게 단건 조회 성공", store);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 가게 전체 조회 기능
     *
     * @return : 등록된 가게 정보
     */
    @GetMapping("/stores")
    public ResponseEntity<DataCommonResponse<List<AdminStoreResponseDto>>> getAllStore(){
        List<AdminStoreResponseDto> stores = storeService.getAllStore();
        DataCommonResponse<List<AdminStoreResponseDto>> response = new DataCommonResponse<>(200,"가게 전체 조회 성공",stores);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
