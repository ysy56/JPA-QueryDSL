package com.sparta.greeypeople.review.service;

import com.sparta.greeypeople.review.dto.response.ReviewResponseDto;
import com.sparta.greeypeople.review.entity.Review;
import com.sparta.greeypeople.review.repository.ReviewRepository;
import com.sparta.greeypeople.store.repository.StoreRepository;
import com.sparta.greeypeople.test.ReviewTest;
import com.sparta.greeypeople.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.sparta.greeypeople.test.StoreTest.TEST_STORE;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class ReviewServiceTest implements ReviewTest {

    @Autowired
    ReviewService reviewService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    StoreRepository storeRepository;

//    @BeforeEach
//    void setUp() {
//        userRepository.save(TEST_USER);
//        storeRepository.save(TEST_STORE);
//        reviewRepository.save(TEST_REVIEW);
//    }

    @DisplayName("리뷰 생성")
    @Test
    void createReview() {
        // given
        storeRepository.save(TEST_STORE);
        userRepository.save(TEST_USER);
        reviewRepository.save(TEST_REVIEW);

        // when
        ReviewResponseDto result = reviewService.createReview(TEST_REVIEW_REQUEST_DTO, TEST_STORE_ID, TEST_USER);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("updateAt")
                .isEqualTo(TEST_REVIEW_RESPONSE_DTO);
    }

    @DisplayName("리뷰 단건 조회")
    @Test
    void getReview() {
        // given
        storeRepository.save(TEST_STORE);
        userRepository.save(TEST_USER);
        reviewRepository.save(TEST_REVIEW);

        // when
        ReviewResponseDto result = reviewService.getReview(TEST_STORE_ID, TEST_REVIEW_ID);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("updateAt")
                .isEqualTo(TEST_REVIEW_RESPONSE_DTO);
    }

    @DisplayName("리뷰 전체 조회")
    @Test
    void getAllReview() {
        // given
        storeRepository.save(TEST_STORE);
        userRepository.save(TEST_USER);

        Review testReview1 = new Review(TEST_REVIEW_REQUEST_DTO, TEST_STORE, TEST_USER);
        Review testReview2 = new Review(TEST_REVIEW_REQUEST_DTO, TEST_STORE, TEST_USER);
        Review testReview3 = new Review(TEST_REVIEW_REQUEST_DTO, TEST_STORE, TEST_USER);

        reviewRepository.save(testReview1);
        reviewRepository.save(testReview2);
        reviewRepository.save(testReview3);

        List<ReviewResponseDto> expectedReviewResponseDtos = List.of(
                new ReviewResponseDto(testReview1),
                new ReviewResponseDto(testReview2),
                new ReviewResponseDto(testReview3)
        );

        // when
        List<ReviewResponseDto> result = reviewService.getAllReviews(TEST_STORE_ID);

        // then
        assertThat(result).usingRecursiveComparison().ignoringFields("updateAt").isEqualTo(expectedReviewResponseDtos);
    }

    @DisplayName("리뷰 수정")
    @Test
    void updateReview() {
        // given
        storeRepository.save(TEST_STORE);
        userRepository.save(TEST_USER);
        reviewRepository.save(TEST_REVIEW);

        // when
        ReviewResponseDto result = reviewService.updateReview(1L, TEST_REVIEW_ID, TEST_REVIEW_REQUEST_DTO, TEST_USER);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("updateAt")
                .isEqualTo(TEST_REVIEW_RESPONSE_DTO);
    }
}
