package com.homework.order.dto;

import com.homework.order.entity.Order;
import com.homework.order.entity.OrderItem;
import com.homework.order.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long id;

    private String email;

    private OrderStatus orderStatus;

    private LocalDateTime orderTime;

    private List<OrderItemResponse> orderItemList;

    private Long totalPrice;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemResponse {
        private Long id;

        private Long itemId;

        private Long orderId;

        private Long count;

        private Long itemPrice;

        public static OrderItemResponse of(OrderItem orderItem) {
            OrderItemResponse orderItemResponse = new OrderItemResponse();
            orderItemResponse.id = orderItem.getId();
            orderItemResponse.itemId = orderItem.getItem().getId();
            orderItemResponse.orderId = orderItem.getOrder().getId();
            orderItemResponse.count = orderItem.getCount();
            orderItemResponse.itemPrice = orderItem.getItemPrice();
            return orderItemResponse;
        }

    }

    public static OrderResponse of(Order order) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.id = order.getId();
        orderResponse.email = order.getEmail();
        orderResponse.orderTime = order.getOrderTime();
        orderResponse.orderStatus = order.getOrderStatus();
        orderResponse.totalPrice = order.getTotalPrice();

        orderResponse.orderItemList = order.getOrderItemList().stream()
                .map(OrderItemResponse::of)
                .collect(Collectors.toList());
        return orderResponse;
    }
}
