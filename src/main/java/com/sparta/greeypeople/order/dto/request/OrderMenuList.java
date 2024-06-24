package com.sparta.greeypeople.order.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderMenuList {

    @NotNull(message = "주문할 메뉴의 Id를 추가해주세요")
    private Long menuId;
}
