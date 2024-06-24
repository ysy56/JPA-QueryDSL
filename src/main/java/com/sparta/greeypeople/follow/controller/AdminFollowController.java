package com.sparta.greeypeople.follow.controller;

import com.sparta.greeypeople.common.DataCommonResponse;
import com.sparta.greeypeople.follow.dto.AdminFollowResponseDto;
import com.sparta.greeypeople.follow.service.AdminFollowService;
import jakarta.validation.constraints.Min;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/stores")
public class AdminFollowController {

    private final AdminFollowService adminFollowService;

    /**
     * 특정 가게를 찜한 사람 조회 기능
     *
     * @param storeId : 찜한 사람을 조회할 가게 Id
     * @return : 특정 가게를 찜한 사람 조회 데이터
     */
    @GetMapping("/{storeId}/follow")
    public ResponseEntity<DataCommonResponse<List<AdminFollowResponseDto>>> getStoreAllFollower(
        @Min(1) @PathVariable Long storeId
    ) {
        List<AdminFollowResponseDto> follow = adminFollowService.findStoreAllFollower(storeId);
        DataCommonResponse<List<AdminFollowResponseDto>> response = new DataCommonResponse<>(200,
            "팔로워 조회 성공", follow);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
