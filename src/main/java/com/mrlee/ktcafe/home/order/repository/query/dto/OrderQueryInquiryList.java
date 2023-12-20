package com.mrlee.ktcafe.home.order.repository.query.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import com.mrlee.ktcafe.home.order.domain.Order;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class OrderQueryInquiryList {

    private Long orderId;
    private Order.OrderStatus orderStatus;
    private String orderProductName;
    private int paymentAmount;
    private LocalDateTime orderTime;
    private boolean hasReview;

    public OrderQueryInquiryList(Order order) {
        this.orderId = order.getId();
        this.orderStatus = order.getOrderStatus();
        this.orderProductName = order.getRepresentativeOrderedProductName();
        this.paymentAmount = order.getPayment().getPaymentAmount();
        this.orderTime = order.getOrderTime();
        this.hasReview = order.getReview() != null;
    }

}
