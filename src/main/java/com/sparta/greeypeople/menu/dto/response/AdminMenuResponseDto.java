package com.sparta.greeypeople.menu.dto.response;

import com.sparta.greeypeople.menu.entity.Menu;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminMenuResponseDto {

    private String menuName;
    private int price;

    public AdminMenuResponseDto(Menu menu) {
        this.menuName = menu.getMenu();
        this.price = menu.getPrice();
    }

}
