package com.ecommerce.project_backend.dto;

import com.ecommerce.project_backend.entity.PaymentStatus;

import java.time.LocalDateTime;
import java.util.List;

public class OrderResponseDTO {

    private Long orderId;
    private String userName;
    private double totalAmount;
    private LocalDateTime orderDate;
    private PaymentStatus paymentStatus;
    private String orderStatus;

    private List<OrderItemDTO> items;

    public OrderResponseDTO() {}

    public Long getOrderId() {
        return orderId;
    }

    public String getUserName() {
        return userName;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }
}
