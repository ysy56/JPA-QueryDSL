package com.sparta.greeypeople.order.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OrderMenuList {

    @NotNull(message = "주문할 메뉴의 Id를 추가해주세요")
    private Long menuId;
}
