package com.sparta.greeypeople.order.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderRequestDto {

    @NotEmpty(message = "주문할 메뉴를 추가해주세요")
    @Valid
    List<OrderMenuList> menuList;
}
