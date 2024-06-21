package com.sparta.greeypeople.like.review;

import com.sparta.greeypeople.review.entity.Review;
import com.sparta.greeypeople.timestamp.TimeStamp;

import com.sparta.greeypeople.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "ReviewLikes_table")
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

    @Builder
    public ReviewLikes(User user, Review review) {
        this.user = user;
        this.review = review;
    }
}