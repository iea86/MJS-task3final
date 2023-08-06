package com.epam.ems.dto;

import org.springframework.hateoas.RepresentationModel;

import java.util.List;

public class OrderDTO  extends RepresentationModel<OrderDTO> {
    private Long orderId;
    private Long userId;
    private String orderDate;
    private double cost;
    private List<OrderDetailDTO> orderDetails;

    public OrderDTO() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public List<OrderDetailDTO> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailDTO> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", orderDate='" + orderDate + '\'' +
                ", orderDetails=" + orderDetails +
                '}';
    }
}
