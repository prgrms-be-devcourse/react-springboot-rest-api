package com.example.gccoffee.model;

public enum OrderStatus {
    ACCEPTED, // 주문을 받고
    PAYMENT_CONFIRMED, // 결제가 완료되면
    READY_FOR_DELIVERY, // 그 다음날 2시 이후에 상태 변경
    SHIPPED,
    SETTLED,
    CANCELLED
}
