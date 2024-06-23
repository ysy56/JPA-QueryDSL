package com.sparta.greeypeople.order.service;

import com.sparta.greeypeople.exception.DataNotFoundException;
import com.sparta.greeypeople.exception.ForbiddenException;
import com.sparta.greeypeople.menu.entity.Menu;
import com.sparta.greeypeople.menu.repository.MenuRepository;
import com.sparta.greeypeople.order.dto.request.OrderMenuList;
import com.sparta.greeypeople.order.dto.request.OrderRequestDto;
import com.sparta.greeypeople.order.dto.response.OrderResponseDto;
import com.sparta.greeypeople.order.entity.Order;
import com.sparta.greeypeople.order.entity.OrderMenu;
import com.sparta.greeypeople.order.enumeration.Process;
import com.sparta.greeypeople.order.repository.OrderMenuRepository;
import com.sparta.greeypeople.order.repository.OrderRepository;
import com.sparta.greeypeople.store.entity.Store;
import com.sparta.greeypeople.store.repository.StoreRepository;
import com.sparta.greeypeople.user.entity.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;

    //주문 등록
    @Transactional
    public OrderResponseDto createOrder(Long storeId, OrderRequestDto orderRequest, User user) {
        Store store = storeRepository.findById(storeId).orElseThrow(
            () -> new DataNotFoundException("해당 가게는 존재하지 않습니다")
        );
        Process process = Process.COMPLETED;

        List<Long> ids = orderRequest.getMenuList().stream()
            .map(OrderMenuList::getMenuId)
            .toList();

        List<Menu> menus = menuRepository.findAllById(ids);

        Order order = new Order(store, user, process);

        List<OrderMenu> orderMenus = menus.stream().map(OrderMenu::new).toList();
        orderMenus.forEach(order::addOrderMenu);

        Order savedOrder = orderRepository.save(order);

        if (!order.getUser().getId().equals(user.getId())) {
            throw new ForbiddenException("주문 작성 권한이 없습니다.");
        }
        return new OrderResponseDto(savedOrder);
    }

    //주문 단건 조회
    public OrderResponseDto getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
            () -> new DataNotFoundException("해당 주문은 존재하지 않습니다")
        );
        return new OrderResponseDto(order);
    }

    //주문 전체 조회
//    public List<OrderResponseDto> getAllOrder() {
//        return orderRepository.findAll().stream().map(OrderResponseDto::new).toList();
//    }

//페이징 시도
    @Transactional(readOnly = true)
    public Page<OrderResponseDto> getAllOrder(int page, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ?  Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, 5, sort);
        Page<Order> orderList;
        orderList = orderRepository.findAll(pageable);
        return orderList.map(OrderResponseDto::new);
    }

    //주문 수정
    @Transactional
    public OrderResponseDto updateOrdeer(Long orderId, OrderRequestDto orderRequest, User user) {
       Order order = orderRepository.findById(orderId).orElseThrow(
            () -> new DataNotFoundException("해당 주문은 존재하지 않습니다")
        );
        if (!order.getUser().getId().equals(user.getId())) {
            throw new ForbiddenException("수정 권한이 없습니다.");
        }

        List<Long> ids = orderRequest.getMenuList().stream()
            .map(OrderMenuList::getMenuId)
            .collect(Collectors.toList());

        List<Menu> menus = menuRepository.findAllById(ids);

        List<OrderMenu> orderMenus = menus.stream()
            .map(OrderMenu::new)
            .collect(Collectors.toList());
        order.update(orderMenus);

        Order savedOrder = orderRepository.save(order);
        return new OrderResponseDto(savedOrder);
    }

    //주문 삭제
    public void deleteOrder(Long orderId, User user) {
        Order order = orderRepository.findById(orderId).orElseThrow(
            () -> new DataNotFoundException("해당 주문은 존재하지 않습니다")
        );
        if (!order.getUser().getId().equals(user.getId())) {
            throw new ForbiddenException("삭제 권한이 없습니다.");
        }
//        Process process = Process.CANCELED;
        orderRepository.delete(order);
    }
}

