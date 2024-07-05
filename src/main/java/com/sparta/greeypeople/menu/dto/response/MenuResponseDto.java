package com.sparta.greeypeople.menu.dto.response;

import com.sparta.greeypeople.menu.entity.Menu;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenuResponseDto {

    private Long storeId;
    private String menuName;
    private int price;
    private Long menuLikes;

    public MenuResponseDto(Menu menu) {
        this.storeId = menu.getStore().getId();
        this.menuName = menu.getMenuName();
        this.price = menu.getPrice();
        this.menuLikes = menu.getMenuLikes();
    }

}