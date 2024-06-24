package com.sparta.greeypeople.like.entity;

import com.sparta.greeypeople.review.entity.Review;
import com.sparta.greeypeople.common.TimeStamp;

import com.sparta.greeypeople.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "review_likes_table")
@NoArgsConstructor
public class ReviewLikes extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    public ReviewLikes(User user, Review review) {
        this.user = user;
        this.review = review;
    }
}