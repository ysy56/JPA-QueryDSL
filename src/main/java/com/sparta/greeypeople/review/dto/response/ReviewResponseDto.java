package com.sparta.greeypeople.review.dto.response;

import com.sparta.greeypeople.review.entity.Review;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewResponseDto {

    private String content;
    private LocalDateTime updateAt;
    private Long reviewLikes;

    public ReviewResponseDto(Review review) {
        this.content = review.getContent();
        this.updateAt = review.getModifiedAt();
        this.reviewLikes = review.getReviewLikes();
    }
}
