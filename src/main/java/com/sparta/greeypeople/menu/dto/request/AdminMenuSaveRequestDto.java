package com.sparta.greeypeople.menu.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminMenuSaveRequestDto {

    @NotBlank(message = "메뉴를 입력해주세요.")
    private String menuName;

    @NotBlank(message = "메뉴 가격을 입력해주세요.")
    private int price;

}
