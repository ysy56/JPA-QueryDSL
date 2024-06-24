package com.sparta.greeypeople.review.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewRequestDto {

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;
}
