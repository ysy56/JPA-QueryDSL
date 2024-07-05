package com.sparta.greeypeople.review.repository;

import static com.sparta.greeypeople.test.ReviewTest.*;
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
@ActiveProfiles("test")
class ReviewRepositoryTest {
    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    UserRepository userRepository;

//    @BeforeEach
//    void setUp() {
//        userRepository.save(TEST_USER);
//        storeRepository.save(TEST_STORE);
//        reviewRepository.save(TEST_REVIEW);
//    }

    @Test
    @DisplayName("생성일시 기준 내림차순 정렬 조회")
    void findAll(){
        // given
        userRepository.save(TEST_USER);
        storeRepository.save(TEST_STORE);
        reviewRepository.save(TEST_REVIEW);
        reviewRepository.save(TEST_ANOTHER_REVIEW);

        // when
        var resultReviewList = reviewRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));

        // then
        assertThat(resultReviewList.get(0))
                .usingRecursiveComparison()
                .ignoringFields("createdAt")
                .isEqualTo(TEST_REVIEW);
        assertThat(resultReviewList.get(1))
                .usingRecursiveComparison()
                .ignoringFields("createdAt")
                .isEqualTo(TEST_ANOTHER_REVIEW);
    }
}