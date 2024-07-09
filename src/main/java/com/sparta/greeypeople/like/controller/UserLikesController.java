package com.sparta.greeypeople.like.controller;

import com.sparta.greeypeople.auth.security.UserDetailsImpl;
import com.sparta.greeypeople.common.DataCommonResponse;
import com.sparta.greeypeople.common.StatusCommonResponse;
import com.sparta.greeypeople.like.service.MenuLikesService;
import com.sparta.greeypeople.like.service.UserLikesService;
import com.sparta.greeypeople.menu.dto.response.MenuResponseDto;
import com.sparta.greeypeople.menu.entity.Menu;
import com.sparta.greeypeople.order.dto.response.OrderResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/likes")
public class UserLikesController {

    private final UserLikesService userLikesService;

    /**
     * 좋아요한 메뉴 조회 기능
     * @param page : 조회하려는 페이지
     * @param size : 페이지 당 불러올 데이터의 수
     * @param userDetails : 사용자 정보
     * @return : 좋아요한 메뉴 조회 메시지 및 상태 코드 반환
     */
    @GetMapping
    public ResponseEntity<DataCommonResponse<List<MenuResponseDto>>> addMenuLike(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Page<MenuResponseDto> responseDto = userLikesService.getLikedMenus(userDetails.getUser(), page, size);
        List<MenuResponseDto> menus = responseDto.getContent();
        DataCommonResponse<List<MenuResponseDto>> commonResponse = new DataCommonResponse<>(201, "좋아요 메뉴 조회 성공", menus);
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }
}
