package com.sparta.greeypeople.review.dto.response;

import com.sparta.greeypeople.review.entity.Review;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ReviewResponseDto {

    private final String content;
    private final LocalDateTime updateAt;

    public ReviewResponseDto(Review review) {
        this.content = review.getContent();
        this.updateAt = review.getModifiedAt();
    }
}
