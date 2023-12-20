package com.mrlee.ktcafe.home.order.service.dto;

import com.mrlee.ktcafe.home.order.domain.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderInquiry {

    private OrderSummary orderSummary;
    private List<OrderProductInquiry> orderProductInquiry = new ArrayList<>();
    private PaymentInquiry paymentInquiry;
    private boolean hasReview;

    public OrderInquiry(Order order) {
        this.orderSummary = new OrderSummary(order);
        this.orderProductInquiry = order.getOrderProducts().stream().map(OrderProductInquiry::new).toList();
        this.paymentInquiry = new PaymentInquiry(order.getPayment());
        this.hasReview = order.getReview() != null;
    }

    @Data
    @NoArgsConstructor
    public static class OrderSummary {
        private Long orderId;
        private String representativeOrderProductName;
        private LocalDateTime orderTime;

        public OrderSummary(Order order) {
            this.orderId = order.getId();
            this.representativeOrderProductName = order.getRepresentativeOrderedProductName();
            this.orderTime = order.getOrderTime();
        }
    }

    @Data
    @NoArgsConstructor
    public static class OrderProductInquiry {
        private String name;
        private int price;
        private int count;
        private List<OrderOptionGroupInquiry> orderOptionGroupInquiry = new ArrayList<>();
        private int totalAmount;

        public OrderProductInquiry(OrderProduct orderProduct) {
            this.name = orderProduct.getName();
            this.price = orderProduct.getPrice();
            this.count = orderProduct.getCount();
            this.totalAmount = orderProduct.calculateTotalAmountWithOrderOption();
            this.orderOptionGroupInquiry = orderProduct.getOrderOptionGroups().stream().map(OrderOptionGroupInquiry::new).toList();
        }

        @Data
        @NoArgsConstructor
        public static class OrderOptionGroupInquiry {
            private String name;
            private List<OrderOptionInquiryList> orderOptionInquiryList = new ArrayList<>();

            public OrderOptionGroupInquiry(OrderOptionGroup orderOptionGroup) {
                this.name = orderOptionGroup.getName();
                this.orderOptionInquiryList = orderOptionGroup.getOrderOptions().stream().map(OrderOptionInquiryList::new).toList();
            }

            @Data
            @NoArgsConstructor
            public static class OrderOptionInquiryList {
                private String name;
                private int price;

                public OrderOptionInquiryList(OrderOption orderOption) {
                    this.name = orderOption.getName();
                    this.price = orderOption.getPrice();
                }
            }

        }
    }

    @Data
    @NoArgsConstructor
    public static class PaymentInquiry {
        private int paymentAmount;
        private int discountAmount;
        private int totalAmount;
        private Payment.PaymentMethod paymentMethod;

        public PaymentInquiry(Payment payment) {
            this.paymentAmount = payment.getPaymentAmount();
            this.discountAmount = payment.getDiscountAmount();
            this.totalAmount = payment.getTotalAmount();
            this.paymentMethod = payment.getPaymentMethod();
        }
    }
}
