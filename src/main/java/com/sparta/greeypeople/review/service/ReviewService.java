package com.sparta.greeypeople.review.service;

import com.sparta.greeypeople.exception.DataNotFoundException;
import com.sparta.greeypeople.review.dto.request.ReviewRequestDto;
import com.sparta.greeypeople.review.dto.response.ReviewResponseDto;
import com.sparta.greeypeople.review.entity.Review;
import com.sparta.greeypeople.review.repository.ReviewRepository;
import com.sparta.greeypeople.store.entity.Store;
import com.sparta.greeypeople.store.repository.StoreRepository;
import com.sparta.greeypeople.user.entity.User;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;

    public ReviewService (ReviewRepository reviewRepository, StoreRepository storeRepository){
        this.reviewRepository = reviewRepository;
        this.storeRepository = storeRepository;
    }

    public ReviewResponseDto createReview(ReviewRequestDto reviewRequestDto, Long storeId, User user) {
        Store store = storeRepository.findById(storeId).orElseThrow(
            () -> new DataNotFoundException("해당 가게는 존재하지 않습니다")
        );
        Review review = reviewRepository.save(new Review(reviewRequestDto,store,user));
        return new ReviewResponseDto(review);
    }
}
