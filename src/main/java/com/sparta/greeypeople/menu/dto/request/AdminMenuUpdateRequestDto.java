package com.sparta.greeypeople.menu.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminMenuUpdateRequestDto {

    @NotBlank(message = "변경할 메뉴를 입력해주세요.")
    private String menuName;

    @Positive(message = "메뉴 가격을 입력해주세요.")
    private int price;

}
