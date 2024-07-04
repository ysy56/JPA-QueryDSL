package com.sparta.greeypeople.review.repository;

import static com.sparta.greeypeople.test.ReviewTest.TEST_REVIEW;
import static com.sparta.greeypeople.test.ReviewTest.TEST_REVIEW_REQUEST_DTO;
import static com.sparta.greeypeople.test.StoreTest.TEST_STORE;
import static com.sparta.greeypeople.test.UserTest.TEST_USER;
import static com.sparta.greeypeople.test.UserTest.TEST_USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.sparta.greeypeople.review.dto.response.ReviewResponseDto;
import com.sparta.greeypeople.review.entity.Review;
import com.sparta.greeypeople.store.repository.StoreRepository;
import com.sparta.greeypeople.user.entity.User;
import com.sparta.greeypeople.user.repository.UserRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles // 찾아보기
class ReviewRepositoryTest {
    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        // 기존 데이터를 삭제
        reviewRepository.deleteAll();
        storeRepository.deleteAll();
        userRepository.deleteAll();

        // Store와 User를 먼저 저장
        userRepository.save(TEST_USER);
        storeRepository.save(TEST_STORE);
    }

    @Test
    @DisplayName("생성일시 기준 내림차순 정렬 조회")
    void findAll(){
        // given
        User user = userRepository.findByUserId(TEST_USER_ID).get();

        Review mock1 = Mockito.mock(Review.class); // given when then 적용
        Review mock2 = Mockito.mock(Review.class); // given when then 적용

        // when
        Mockito.when(mock1.getCreatedAt()).thenReturn(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
        Mockito.when(mock1.getContent()).thenReturn("content1");
        Mockito.when(mock2.getCreatedAt()).thenReturn(LocalDateTime.of(2021, 2, 1, 0, 0, 0));
        Mockito.when(mock2.getContent()).thenReturn("content2");

        reviewRepository.save(mock1);
        reviewRepository.save(mock2);

        var resultReviewList = reviewRepository.findAll(Sort.by(Sort.Direction.DESC, "createDate"));

        // then
        assertThat(resultReviewList.get(0)).isEqualTo(mock2);
        assertThat(resultReviewList.get(1)).isEqualTo(mock1);
    }
}