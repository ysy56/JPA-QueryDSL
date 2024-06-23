package com.sparta.greeypeople.review.service;

import com.sparta.greeypeople.exception.DataNotFoundException;
import com.sparta.greeypeople.exception.ForbiddenException;
import com.sparta.greeypeople.review.dto.request.ReviewRequestDto;
import com.sparta.greeypeople.review.dto.response.ReviewResponseDto;
import com.sparta.greeypeople.review.entity.Review;
import com.sparta.greeypeople.review.repository.ReviewRepository;
import com.sparta.greeypeople.store.entity.Store;
import com.sparta.greeypeople.store.repository.StoreRepository;
import com.sparta.greeypeople.user.entity.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
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

    public ReviewResponseDto getReview(Long storeId, Long reviewId) {
        storeRepository.findById(storeId).orElseThrow(
            () -> new DataNotFoundException("해당 가게는 존재하지 않습니다")
        );

        Review review = reviewRepository.findById(reviewId).orElseThrow(
            () -> new DataNotFoundException("해당 리뷰는 존재하지 않습니다")
        );
        return new ReviewResponseDto(review);
    }

    public List<ReviewResponseDto> getAllReviews(Long storeId) {
        List<Review> reviews = reviewRepository.findAllByStoreId(storeId);

        return reviews.stream().map(ReviewResponseDto::new).collect(Collectors.toList());
    }

    public ReviewResponseDto updateReview(Long storeId, Long reviewId, ReviewRequestDto reviewRequestDto, User user) {
        Store store = storeRepository.findById(storeId).orElseThrow(
            () -> new DataNotFoundException("해당 가게는 존재하지 않습니다")
        );

        Review review = reviewRepository.findById(reviewId).orElseThrow(
            () -> new DataNotFoundException("해당 리뷰는 존재하지 않습니다")
        );

        if (!review.getUser().getId().equals(user.getId())) {
            throw new IllegalStateException("수정 권한이 없습니다.");
        }

        if(review.getCreatedAt().isBefore(LocalDateTime.now().minusMinutes(1))) {
            throw new ForbiddenException("댓글 수정 기간이 지났습니다.");
        }

        review.update(reviewRequestDto);
        reviewRepository.save(review);

        return new ReviewResponseDto(review);
    }

    public void deleteReview(Long storeId, Long reviewId, User user) {
        Store store = storeRepository.findById(storeId).orElseThrow(
            () -> new DataNotFoundException("해당 가게는 존재하지 않습니다")
        );

        Review review = reviewRepository.findById(reviewId).orElseThrow(
            () -> new DataNotFoundException("해당 리뷰는 존재하지 않습니다")
        );

        if (!review.getUser().getId().equals(user.getId())) {
            throw new IllegalStateException("삭제 권한이 없습니다.");
        }
        reviewRepository.delete(review);
    }
}
