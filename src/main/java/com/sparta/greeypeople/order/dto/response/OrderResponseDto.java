package com.sparta.greeypeople.order.dto.response;

import com.sparta.greeypeople.menu.dto.response.AdminMenuResponseDto;
import com.sparta.greeypeople.order.entity.Order;
import com.sparta.greeypeople.order.entity.OrderMenu;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class OrderResponseDto {

    private final String storeName;
    private final List<AdminMenuResponseDto> menuList;

    public OrderResponseDto(Order order) {
        this.storeName = order.getStore().getStoreName();
        this.menuList = order.getOrderMenus().stream()
            .map(orderMenu -> new AdminMenuResponseDto(orderMenu.getMenu()))
            .collect(Collectors.toList());
    }
}
