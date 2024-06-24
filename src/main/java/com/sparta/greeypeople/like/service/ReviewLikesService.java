package com.sparta.greeypeople.like.service;

import com.sparta.greeypeople.like.entity.ReviewLikes;
import com.sparta.greeypeople.like.repository.ReviewLikesRepository;
import com.sparta.greeypeople.review.entity.Review;
import com.sparta.greeypeople.review.repository.ReviewRepository;
import com.sparta.greeypeople.user.entity.User;
import com.sparta.greeypeople.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.greeypeople.exception.BadRequestException;
import com.sparta.greeypeople.exception.DataNotFoundException;
import com.sparta.greeypeople.exception.ForbiddenException;
import com.sparta.greeypeople.exception.ViolatedException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewLikesService {

    private final ReviewLikesRepository reviewLikesRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public void addReviewLike(Long reviewId, User user) {

        User foundUser = userRepository.findById(user.getId()).orElseThrow(() ->
            new DataNotFoundException("해당 사용자가 존재하지 않습니다."));
        Review foundReview = reviewRepository.findById(reviewId).orElseThrow(() ->
            new DataNotFoundException("해당 리뷰가 존재하지 않습니다."));

        if (foundUser.getUserId().equals(foundReview.getUser().getUserId())) {
            throw new BadRequestException("자신이 등록한 리뷰에는 좋아요를 남길 수 없습니다.");
        }

        ReviewLikes reviewLikes  = new ReviewLikes(foundUser, foundReview);

        if (reviewLikesRepository.findByUserAndReview(foundUser, foundReview).isPresent()) {
            throw new ViolatedException("이미 좋아요를 누른 리뷰입니다.");
        }

        reviewLikesRepository.save(reviewLikes);
        foundReview.addLike();
    }

    @Transactional
    public void removeReviewLike(Long reviewLikeId, User user) {

        ReviewLikes foundlike = reviewLikesRepository.findById(reviewLikeId).orElseThrow(
            () -> new DataNotFoundException("해당 좋아요가 존재하지 않습니다."));

        Review foundReview = reviewRepository.findById(foundlike.getReview().getId()).orElseThrow(
            () -> new DataNotFoundException("해당 리뷰가 존재하지 않습니다."));

        if (!(user.getUserId().equals(foundlike.getUser().getUserId()))) {
            throw new ForbiddenException("다른 사람의 좋아요는 삭제할 수 없습니다.");
        }

        reviewLikesRepository.delete(foundlike);
        foundReview.minusLike();
    }

}