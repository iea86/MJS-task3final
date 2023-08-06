package com.epam.ems.dto;



public class OrderDetailDTO {
    private Long orderDetailId;
    private Long certificateId;
    private String certificateName;
    private Double certificatePrice;
    private int quantity;

    public Long getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Long orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public Long getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(Long certificateId) {
        this.certificateId = certificateId;
    }

    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }

    public Double getCertificatePrice() {
        return certificatePrice;
    }

    public void setCertificatePrice(Double certificatePrice) {
        this.certificatePrice = certificatePrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderDetailDTO{" +
                "orderDetailId=" + orderDetailId +
                ", certificateId=" + certificateId +
                ", certificateName='" + certificateName + '\'' +
                ", certificatePrice=" + certificatePrice +
                ", quantity=" + quantity +
                '}';
    }
}
