package com.sparta.greeypeople.like.review;

import com.sparta.greeypeople.auth.security.UserDetailsImpl;
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

    @PostMapping("/{reviewId}/like")
    public ResponseEntity<StatusCommonResponse> addReviewLike(@PathVariable Long reviewId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            reviewLikesService.addReviewLike(reviewId, userDetails.getUser());
            StatusCommonResponse commonResponse = new StatusCommonResponse(201, "리뷰 좋아요 등록 성공");
            return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new StatusCommonResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        } catch (ViolatedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new StatusCommonResponse(HttpStatus.CONFLICT.value(), e.getMessage()));
        }
    }

    @DeleteMapping("/{reviewLikeId}/like")
    public ResponseEntity<StatusCommonResponse> removeReviewLike(@PathVariable Long reviewLikeId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            reviewLikesService.removeReviewLike(reviewLikeId, userDetails.getUser());
            StatusCommonResponse commonResponse = new StatusCommonResponse(204, "리뷰 좋아요 삭제 성공");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(commonResponse);
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StatusCommonResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        } catch (ForbiddenException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new StatusCommonResponse(HttpStatus.FORBIDDEN.value(), e.getMessage()));
        }
    }

}