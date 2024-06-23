package com.sparta.greeypeople.order.dto.response;

import com.sparta.greeypeople.order.entity.Order;
import com.sparta.greeypeople.order.entity.OrderMenu;
import java.util.List;
import lombok.Getter;

@Getter
public class OrderResponseDto {
    private final String storeName;
    private final List<OrderMenu> menuList;

    public OrderResponseDto(Order order) {
        this.storeName = order.getStore().getStoreName();
        this.menuList = order.getOrderMenus();
    }
}
