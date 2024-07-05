package com.sparta.greeypeople.test;

import com.sparta.greeypeople.review.dto.request.ReviewRequestDto;
import com.sparta.greeypeople.review.dto.response.ReviewResponseDto;
import com.sparta.greeypeople.review.entity.Review;
import com.sparta.greeypeople.user.dto.request.SignupRequestDto;
import com.sparta.greeypeople.user.entity.User;
import com.sparta.greeypeople.user.enumeration.UserAuth;
import com.sparta.greeypeople.user.enumeration.UserStatus;

import static com.sparta.greeypeople.test.StoreTest.TEST_STORE;

public interface ReviewTest extends UserTest {
    Long TEST_STORE_ID = 1L;
    Long TEST_REVIEW_ID = 1L;
    String TEST_ANOTHER_REVIEW_CONTENT = "Another Review Content";
    String TEST_REVIEW_CONTENT = "test review content";

    ReviewRequestDto TEST_REVIEW_REQUEST_DTO = new ReviewRequestDto(
            TEST_REVIEW_CONTENT
    );
    ReviewRequestDto TEST_ANOTHER_REVIEW_RESPONSE_DTO = new ReviewRequestDto(
            TEST_ANOTHER_REVIEW_CONTENT
    );

    Review TEST_REVIEW = new Review(TEST_REVIEW_REQUEST_DTO, TEST_STORE, TEST_USER);
    Review TEST_ANOTHER_REVIEW = new Review(TEST_ANOTHER_REVIEW_RESPONSE_DTO, TEST_STORE, TEST_USER);

    ReviewResponseDto TEST_REVIEW_RESPONSE_DTO = new ReviewResponseDto(TEST_REVIEW);
//    ReviewResponseDto TEST_ANOTHER_RESPONSE_DTO = new ReviewResponseDto(TEST_ANOTHER_REVIEW);

}
