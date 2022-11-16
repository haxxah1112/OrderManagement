package com.homework.order.entity;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PLACED("주문신청"),
    ACCEPT("주문접수"),
    COMPLETE("주문완료"),
    CANCEL("주문취소"),
    FAILED("주문실패");

    private String message;

    OrderStatus(String message) {
        this.message = message;
    }
}
