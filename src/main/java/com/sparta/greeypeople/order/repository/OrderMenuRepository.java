package com.sparta.greeypeople.order.repository;

import com.sparta.greeypeople.order.entity.Order;
import com.sparta.greeypeople.order.entity.OrderMenu;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMenuRepository extends JpaRepository<OrderMenu, Long> {
    List<OrderMenu> findByIdIn(List<Long> ids);
}
