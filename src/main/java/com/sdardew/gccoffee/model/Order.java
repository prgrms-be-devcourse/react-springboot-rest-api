package com.sdardew.gccoffee.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Order {
  private final UUID orderId;
  private final Email email;
  private String address;
  private String postcode;
  private final List<OrderItem> orderItem;
  private OrderStatus orderStatus;
  private final LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public Order(UUID orderId, Email email, String address, String postcode, List<OrderItem> orderItem, OrderStatus orderStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.orderId = orderId;
    this.email = email;
    this.address = address;
    this.postcode = postcode;
    this.orderItem = orderItem;
    this.orderStatus = orderStatus;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public UUID getOrderId() {
    return orderId;
  }

  public Email getEmail() {
    return email;
  }

  public String getAddress() {
    return address;
  }

  public String getPostcode() {
    return postcode;
  }

  public List<OrderItem> getOrderItem() {
    return orderItem;
  }

  public OrderStatus getOrderStatus() {
    return orderStatus;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setAddress(String address) {
    this.address = address;
    this.updatedAt = LocalDateTime.now();
  }

  public void setPostcode(String postcode) {
    this.postcode = postcode;
    this.updatedAt = LocalDateTime.now();
  }

  public void setOrderStatus(OrderStatus orderStatus) {
    this.orderStatus = orderStatus;
    this.updatedAt = LocalDateTime.now();
  }
}
