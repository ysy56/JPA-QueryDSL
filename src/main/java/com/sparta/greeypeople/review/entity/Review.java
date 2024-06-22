package com.sparta.greeypeople.review.entity;

import com.sparta.greeypeople.review.dto.request.ReviewRequestDto;
import com.sparta.greeypeople.store.entity.Store;
import com.sparta.greeypeople.common.TimeStamp;
import com.sparta.greeypeople.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "review")
public class Review extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", length=600, nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column
    private Long reviewLikes;

    public void addLike() {
        this.reviewLikes = reviewLikes + 1L;
    }

    public void minusLike() {
        this.reviewLikes = reviewLikes - 1L;
    }

    public Review(ReviewRequestDto reviewRequestDto, Store store, User user) {
        this.content = reviewRequestDto.getContent();
        this.store = store;
        this.user = user;
    }

    public void update(ReviewRequestDto reviewRequestDto) {
        this.content = reviewRequestDto.getContent();
    }
}
