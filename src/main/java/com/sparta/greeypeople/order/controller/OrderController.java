package com.sparta.greeypeople.order.controller;

import com.sparta.greeypeople.auth.security.UserDetailsImpl;
import com.sparta.greeypeople.common.DataCommonResponse;
import com.sparta.greeypeople.common.StatusCommonResponse;
import com.sparta.greeypeople.order.dto.request.OrderRequestDto;
import com.sparta.greeypeople.order.dto.response.OrderResponseDto;
import com.sparta.greeypeople.order.service.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * 주문 등록 기능
     *
     * @param storeId    : 주문 등록 할 가게의 Id
     * @param orderRequest : 등록할 주문 정보
     * @return : 등록 주문 정보
     */
    @PostMapping("/stores/{storeId}/order")
    public ResponseEntity<DataCommonResponse<OrderResponseDto>>createOrder(
        @PathVariable Long storeId,
        @RequestBody OrderRequestDto orderRequest,
        @AuthenticationPrincipal UserDetailsImpl userDetails){
        OrderResponseDto responseDto = orderService.createOrder(storeId, orderRequest, userDetails.getUser());
        DataCommonResponse<OrderResponseDto> response = new DataCommonResponse<>(201,"주문 작성 성공", responseDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * 주문 단건 조회 기능
     *
     * @param orderId    : 조회할 주문 Id
     * @return : 등록된 주문 정보
     */
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<DataCommonResponse<OrderResponseDto>>getOrder(
        @PathVariable Long orderId
    ){
        OrderResponseDto order = orderService.getOrder(orderId);
        DataCommonResponse<OrderResponseDto> response = new DataCommonResponse<>(200, "주문 단건 조회 성공", order);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 주문 단건 전체 기능
     *
     * @return : 등록된 주문 정보
     */
    @Transactional(readOnly = true)
    @GetMapping("/orders")
    public ResponseEntity<DataCommonResponse<List<OrderResponseDto>>>getAllOrder(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
        @RequestParam(value = "isAsc", defaultValue = "true") boolean isAsc
    ){
        Page<OrderResponseDto> ordersPage = orderService.getAllOrder(page,sortBy,isAsc);
        List<OrderResponseDto> orders = ordersPage.getContent();
        DataCommonResponse<List<OrderResponseDto>> response = new DataCommonResponse<>(200, "주문 전체 조회 성공", orders);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    /**
     * 주문 수정 기능
     *
     * @param orderId    : 수정할 주문 Id
     * @param orderRequestDto : 수정할 주문 정보
     * @return : 수정된 주문 정보
     */
    @PutMapping("/orders/{orderId}")
    public ResponseEntity<DataCommonResponse<OrderResponseDto>>updateOrder(
        @PathVariable Long orderId,
        @RequestBody OrderRequestDto orderRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        OrderResponseDto responseDto = orderService.updateOrdeer(orderId,orderRequestDto,userDetails.getUser());
        DataCommonResponse<OrderResponseDto> response = new DataCommonResponse<>(200, "리뷰 수정 성공", responseDto);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    /**
     * 주문 삭제 기능
     *
     * @param orderId    : 삭제할 주문 Id
     * @return : 삭제 성공 메세지
     */
    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<StatusCommonResponse>deleteOrder(
        @PathVariable Long orderId,
        @AuthenticationPrincipal UserDetailsImpl userDetails){
        orderService.deleteOrder(orderId, userDetails.getUser());
        StatusCommonResponse response = new StatusCommonResponse(200,"주문 취소 성공");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
