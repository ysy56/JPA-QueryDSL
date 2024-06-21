package com.sparta.greeypeople.review.controller;

import com.sparta.greeypeople.common.DataCommonResponse;
import com.sparta.greeypeople.order.dto.response.OrderResponseDto;
import com.sparta.greeypeople.review.dto.request.ReviewRequestDto;
import com.sparta.greeypeople.review.dto.response.ReviewResponseDto;
import com.sparta.greeypeople.review.service.ReviewService;
import com.sparta.greeypeople.user.service.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    //리뷰 등록
    @PostMapping("/stores/{storeId}/review")
    public ResponseEntity<DataCommonResponse<ReviewResponseDto>> createReview(
        @Valid @PathVariable Long storeId,
        @RequestBody ReviewRequestDto reviewRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails){
        ReviewResponseDto responseDto = reviewService.createReview(reviewRequestDto, storeId, userDetails.getUser());
        DataCommonResponse<ReviewResponseDto> response = new DataCommonResponse<>(200,"리뷰 등록 성공", responseDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
