package com.sparta.greeypeople.menu.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenuResponseDto {

    private Long storeId;
    private String menuName;
    private int price;

    public MenuResponseDto(Long storeId, String menuName, Integer price) {
        this.storeId = storeId;
        this.menuName = menuName;
        this.price = price;
    }

}