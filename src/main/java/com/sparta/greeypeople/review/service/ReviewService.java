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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;

    public ReviewResponseDto createReview(ReviewRequestDto reviewRequestDto, Long storeId, User user) {
        Store store = findStore(storeId);
        Review review = reviewRepository.save(new Review(reviewRequestDto,store,user));
        return new ReviewResponseDto(review);
    }

    public ReviewResponseDto getReview(Long storeId, Long reviewId) {
        findStore(storeId);
        Review review = findReview(reviewId);
        return new ReviewResponseDto(review);
    }

    public List<ReviewResponseDto> getAllReviews(Long storeId) {
        List<Review> reviews = reviewRepository.findAllByStoreId(storeId);
        return reviews.stream().map(ReviewResponseDto::new).collect(Collectors.toList());
    }

    public ReviewResponseDto updateReview(Long storeId, Long reviewId, ReviewRequestDto reviewRequestDto, User user) {
        findStore(storeId);
        Review review = findReview(reviewId);

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
        findStore(storeId);
        Review review = findReview(reviewId);

        if (!review.getUser().getId().equals(user.getId())) {
            throw new IllegalStateException("삭제 권한이 없습니다.");
        }

        reviewRepository.delete(review);
    }

    public Store findStore(Long storeId) {
        return storeRepository.findById(storeId).orElseThrow(
            () -> new DataNotFoundException("조회된 가게의 정보가 없습니다.")
        );
    }

    public Review findReview(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(
            () -> new DataNotFoundException("조회된 리뷰 정보가 없습니다")
        );
    }
}
