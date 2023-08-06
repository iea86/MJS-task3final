package com.epam.esm.entity;

import javax.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "ORDER_DETAILS")
public class OrderDetail {

    @Id
    @Column(name = "ORDER_DETAILS_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderDetailsId;

    @OneToOne
    @JoinColumn(name = "CERTIFICATE_ID")
    private Certificate certificate;

    @Column(name = "QUANTITY")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    public OrderDetail() {
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Long getOrderDetailsId() {
        return orderDetailsId;
    }

    public void setOrderDetailsId(Long orderDetailsId) {
        this.orderDetailsId = orderDetailsId;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetail that = (OrderDetail) o;
        return quantity == that.quantity &&
                Objects.equals(orderDetailsId, that.orderDetailsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderDetailsId, quantity);
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderDetailsId=" + orderDetailsId +
                ", certificate=" + certificate +
                ", quantity=" + quantity +
                ", order=" + order +
                '}';
    }
}
