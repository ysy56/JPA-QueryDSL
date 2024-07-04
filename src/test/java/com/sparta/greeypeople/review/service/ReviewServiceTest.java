package com.sparta.greeypeople.review.service;

import com.sparta.greeypeople.review.dto.response.ReviewResponseDto;
import com.sparta.greeypeople.review.entity.Review;
import com.sparta.greeypeople.review.repository.ReviewRepository;
import com.sparta.greeypeople.store.entity.Store;
import com.sparta.greeypeople.store.repository.StoreRepository;
import com.sparta.greeypeople.test.ReviewTest;
import com.sparta.greeypeople.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.sparta.greeypeople.test.StoreTest.TEST_ADMIN_STORE_SAVE_REQUEST_DTO;
import static com.sparta.greeypeople.test.StoreTest.TEST_STORE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest implements ReviewTest {

    @InjectMocks
    ReviewService reviewService;

    @Mock
    ReviewRepository reviewRepository;

    @Mock
    StoreRepository storeRepository;

    @DisplayName("리뷰 생성")
    @Test
    void createReview() {
        // given
        given(storeRepository.findById(TEST_STORE_ID)).willReturn(Optional.of(TEST_STORE));
        given(reviewRepository.save(any(Review.class))).willReturn(TEST_REVIEW);

        // when
        ReviewResponseDto result = reviewService.createReview(TEST_REVIEW_REQUEST_DTO, TEST_STORE_ID, TEST_USER);

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(TEST_REVIEW_RESPONSE_DTO);
    }

    @DisplayName("리뷰 단건 조회")
    @Test
    void getReview() {
        // given
        given(storeRepository.findById(TEST_STORE_ID)).willReturn(Optional.of(TEST_STORE));
        given(reviewRepository.findById(TEST_REVIEW_ID)).willReturn(Optional.of(TEST_REVIEW));

        // when
        ReviewResponseDto result = reviewService.getReview(TEST_STORE_ID, TEST_REVIEW_ID);

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(TEST_REVIEW_RESPONSE_DTO);
    }

    @DisplayName("리뷰 전체 조회")
    @Test
    void getAllReview() {
        // given
        Review testReview1 = new Review(TEST_REVIEW_REQUEST_DTO, TEST_STORE, TEST_USER);
        Review testReview2 = new Review(TEST_REVIEW_REQUEST_DTO, TEST_STORE, TEST_USER);
        Review testReview3 = new Review(TEST_REVIEW_REQUEST_DTO, TEST_STORE, TEST_USER);

        List<ReviewResponseDto> expectedReviewResponseDtos = List.of(
                new ReviewResponseDto(testReview1),
                new ReviewResponseDto(testReview2),
                new ReviewResponseDto(testReview3)
        );

        given(reviewRepository.findAllByStoreId(TEST_STORE_ID)).willReturn(List.of(testReview1, testReview2, testReview3));

        // when
        List<ReviewResponseDto> result = reviewService.getAllReviews(TEST_STORE_ID);

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(expectedReviewResponseDtos);
    }
}