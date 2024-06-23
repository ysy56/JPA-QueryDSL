package com.sparta.greeypeople.menu.controller;

import com.sparta.greeypeople.common.DataCommonResponse;
import com.sparta.greeypeople.menu.dto.response.AdminMenuResponseDto;
import com.sparta.greeypeople.menu.service.MenuService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/stores/{storeId}/menus")
    public ResponseEntity<DataCommonResponse<List<AdminMenuResponseDto>>> getStoreMenu(
        @PathVariable Long storeId
    ){
        List<AdminMenuResponseDto> storeMenu = menuService.getStoreMenu(storeId);
        DataCommonResponse<List<AdminMenuResponseDto>> response = new DataCommonResponse<>(200,"가게 전체 메뉴 조회 성공",storeMenu);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
