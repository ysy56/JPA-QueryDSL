package com.sparta.greeypeople.like.controller;

import com.sparta.greeypeople.auth.security.UserDetailsImpl;
import com.sparta.greeypeople.like.service.ReviewLikesService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.sparta.greeypeople.common.StatusCommonResponse;
import com.sparta.greeypeople.exception.BadRequestException;
import com.sparta.greeypeople.exception.DataNotFoundException;
import com.sparta.greeypeople.exception.ForbiddenException;
import com.sparta.greeypeople.exception.ViolatedException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores/{storeId}/reviews")
public class ReviewLikesController {

    private final ReviewLikesService reviewLikesService;


    /**
     * 좋아요 등록 기능
     *
     * @param reviewId      : 좋아요 등록 할 리뷰의 Id
     * @return : 좋아요 등록 메시지 상태 코드 반환
     */
    @PostMapping("/{reviewId}/like")
    public ResponseEntity<StatusCommonResponse> addReviewLike(@PathVariable Long storeId, @PathVariable Long reviewId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        reviewLikesService.addReviewLike(storeId, reviewId, userDetails.getUser());
        StatusCommonResponse commonResponse = new StatusCommonResponse(201, "리뷰 좋아요 등록 성공");
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }


    /**
     * 좋아요 삭제 기능
     *
     * @param reviewLikeId      : 좋아요 삭제 할 리뷰의 좋아요 Id
     * @return : 좋아요 삭제 메시지 상태 코드 반환
     */
    @DeleteMapping("/{reviewLikeId}/like")
    public ResponseEntity<StatusCommonResponse> removeReviewLike(@PathVariable Long storeId, @PathVariable Long reviewLikeId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        reviewLikesService.removeReviewLike(storeId, reviewLikeId, userDetails.getUser());
        StatusCommonResponse commonResponse = new StatusCommonResponse(204, "리뷰 좋아요 삭제 성공");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

}