package com.sparta.greeypeople.like.service;

import com.sparta.greeypeople.exception.BadRequestException;
import com.sparta.greeypeople.exception.DataNotFoundException;
import com.sparta.greeypeople.exception.ForbiddenException;
import com.sparta.greeypeople.exception.ViolatedException;
import com.sparta.greeypeople.like.entity.ReviewLikes;
import com.sparta.greeypeople.like.repository.ReviewLikesRepository;
import com.sparta.greeypeople.review.entity.Review;
import com.sparta.greeypeople.review.repository.ReviewRepository;
import com.sparta.greeypeople.store.entity.Store;
import com.sparta.greeypeople.store.repository.StoreRepository;
import com.sparta.greeypeople.user.entity.User;
import com.sparta.greeypeople.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewLikesService {

    private final ReviewLikesRepository reviewLikesRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public void addReviewLike(Long storeId, Long reviewId, User user) {
        User foundUser = validateAndGetUser(user);
        Review foundReview = validateStoreAndReview(storeId, reviewId);

        if (foundUser.getUserId().equals(foundReview.getUser().getUserId())) {
            throw new BadRequestException("자신이 등록한 리뷰에는 좋아요를 남길 수 없습니다.");
        }

        if (reviewLikesRepository.findByUserAndReview(foundUser, foundReview).isPresent()) {
            throw new ViolatedException("이미 좋아요를 누른 리뷰입니다.");
        }

        ReviewLikes reviewLikes = new ReviewLikes(foundUser, foundReview);
        reviewLikesRepository.save(reviewLikes);
        foundReview.addLike();
    }

    @Transactional
    public void removeReviewLike(Long storeId, Long reviewLikeId, User user) {
        User foundUser = validateAndGetUser(user);
        ReviewLikes foundLike = reviewLikesRepository.findById(reviewLikeId).orElseThrow(
            () -> new DataNotFoundException("해당 좋아요가 존재하지 않습니다."));
        Review foundReview = validateStoreAndReview(storeId, foundLike.getReview().getId());

        if (!foundUser.getUserId().equals(foundLike.getUser().getUserId())) {
            throw new ForbiddenException("다른 사람의 좋아요는 삭제할 수 없습니다.");
        }

        reviewLikesRepository.delete(foundLike);
        foundReview.minusLike();
    }

    private User validateAndGetUser(User user) {
        return userRepository.findById(user.getId()).orElseThrow(() ->
            new DataNotFoundException("해당 사용자가 존재하지 않습니다."));
    }

    private Review validateStoreAndReview(Long storeId, Long reviewId) {
        Store store = storeRepository.findById(storeId).orElseThrow(() ->
            new DataNotFoundException("해당 가게가 존재하지 않습니다."));
        Review foundReview = reviewRepository.findById(reviewId).orElseThrow(() ->
            new DataNotFoundException("해당 리뷰가 존재하지 않습니다."));

        if (!foundReview.getStore().getId().equals(store.getId())) {
            throw new BadRequestException("리뷰가 해당 가게에 존재하지 않습니다.");
        }

        return foundReview;
    }
}