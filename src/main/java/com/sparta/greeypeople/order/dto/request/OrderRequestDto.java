package com.sparta.greeypeople.order.dto.request;

import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.Getter;

@Getter
public class OrderRequestDto {
    @NotBlank(message ="주문할 메뉴를 추가해주세요")
    List<OrderMenuList> menuList;
}
