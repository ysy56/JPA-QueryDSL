package com.sparta.greeypeople.follow.controller;

import com.sparta.greeypeople.auth.security.UserDetailsImpl;
import com.sparta.greeypeople.common.DataCommonResponse;
import com.sparta.greeypeople.common.StatusCommonResponse;
import com.sparta.greeypeople.follow.service.FollowService;
import com.sparta.greeypeople.menu.dto.response.MenuResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/stores/{storeId}/follow")
    public ResponseEntity<StatusCommonResponse> followStore(@PathVariable Long storeId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        followService.followStore(storeId, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(new StatusCommonResponse(201, "스토어 팔로우 성공"));
    }

    @DeleteMapping("/stores/{storeId}/follow")
    public ResponseEntity<StatusCommonResponse> unfollowStore(@PathVariable Long storeId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        followService.unfollowStore(storeId, userDetails.getUser());
        StatusCommonResponse commonResponse = new StatusCommonResponse(204, "스토어 언팔로우 성공");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    @GetMapping("/stores/follow/menus")
    public ResponseEntity<DataCommonResponse<List<MenuResponseDto>>> getMenusFromFollowedStores(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<MenuResponseDto> menus = followService.getMenusFromFollowedStores(userDetails.getUser());
        DataCommonResponse<List<MenuResponseDto>> response = new DataCommonResponse<>(HttpStatus.OK.value(), "팔로우한 가게의 메뉴 목록 최신순 조회 완료", menus);
        return ResponseEntity.ok().body(response);
    }
}