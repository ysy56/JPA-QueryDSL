package com.sparta.greeypeople.like.repository;

import com.sparta.greeypeople.like.entity.ReviewLikes;
import com.sparta.greeypeople.review.entity.Review;
import com.sparta.greeypeople.user.entity.User;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewLikesRepository extends JpaRepository<ReviewLikes, Long> {
    Optional<ReviewLikes> findByUserAndReview(User foundUser, Review foundReview);
}