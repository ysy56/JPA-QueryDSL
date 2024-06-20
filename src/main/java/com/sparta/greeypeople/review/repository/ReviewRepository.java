package com.sparta.greeypeople.review.repository;

import com.sparta.greeypeople.review.entity.Review;
import com.sparta.greeypeople.timestamp.TimeStamp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
