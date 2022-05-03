package com.devcourse.drink.order.model;

public enum OrderStatus {
    ACCEPTED("주문완료"),
    PAYMENT_CONFIRMED("결제승인"),
    READY_FOR_DELIVERY("배송준비"),
    SHIPPING("배송중"),
    SETTLED("배송완료"),
    CANCELLED("주문취소");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
