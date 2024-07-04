package com.sparta.greeypeople.review.controller;

import com.sparta.greeypeople.exception.DataNotFoundException;
import com.sparta.greeypeople.review.dto.request.ReviewRequestDto;
import com.sparta.greeypeople.review.service.ReviewService;
import com.sparta.greeypeople.test.ReviewTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReviewController.class)
class ReviewControllerTest extends ControllerTest implements ReviewTest {

    @MockBean
    private ReviewService reviewService;

    @Test
    @DisplayName("리뷰 생성 요청")
    void postReview() throws Exception {
        // given

        // when
        var action = mockMvc.perform(post("/stores/{storeId}/review", TEST_STORE_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(TEST_REVIEW_REQUEST_DTO)))
                .andDo(print());;

        // then
        action.andExpect(status().isCreated());
        verify(reviewService, times(1)).createReview(any(ReviewRequestDto.class), any(Long.class), eq(TEST_USER));
    }

    @Nested
    @DisplayName("리뷰 조회 요청")
    class getReview {
        @DisplayName("리뷰 조회 요청 성공")
        @Test
        void getReview_success() throws Exception {
            // given
            given(reviewService.getReview(eq(TEST_REVIEW_ID), eq(TEST_STORE_ID))).willReturn(TEST_REVIEW_RESPONSE_DTO);

            // when
            var action = mockMvc.perform(get("/stores/{storeId}/reviews/{reviewId}", TEST_STORE_ID, TEST_REVIEW_ID)
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(print());

            // then
            action
                    .andExpect(status().isOk());
        }

        @DisplayName("리뷰 조회 요청 실패 - 존재하지 않는 ID")
        @Test
        void getTodo_fail_todoIdNotExist() throws Exception {
            // given
            given(reviewService.getReview(eq(TEST_REVIEW_ID), eq(TEST_STORE_ID))).willThrow(new DataNotFoundException("존재하지 않는 ID"));

            // when
            var action = mockMvc.perform(get("/stores/{storeId}/reviews/{reviewId}", TEST_STORE_ID, TEST_REVIEW_ID)
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(print());

            // then
            action
                    .andExpect(status().isNotFound());
        }

    }

}