package com.mrlee.ktcafe.home.order.domain;

import com.mrlee.ktcafe.common.BaseTime;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
@Entity
public class Order extends BaseTime {

    public enum OrderStatus {ORDERED, PAYED, CANCEL}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    private Long memberId;
    private LocalDateTime orderTime;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @OneToOne(mappedBy = "order")
    private Review review;

    @Builder
    public Order(Long id, Long memberId, LocalDateTime orderTime, OrderStatus orderStatus, List<OrderProduct> orderProducts, Payment payment) {
        this.id = id;
        this.memberId = memberId;
        this.orderTime = orderTime;
        this.orderStatus = orderStatus;

        for (OrderProduct orderProduct : orderProducts) {
            mappingOrderProduct(orderProduct);
        }
    }

    public void placeOrder(OrderValidator orderValidator) {
        orderValidator.validate(this);
        orderStatus = OrderStatus.ORDERED;
    }

    public void payed() {
        if (!orderStatus.equals(OrderStatus.ORDERED)) {
            throw new IllegalArgumentException("주문완료인 경우만 결제 요청이 가능합니다.");
        }
        orderStatus = OrderStatus.PAYED;
    }

    public void canceled() {
        if (!orderStatus.equals(OrderStatus.ORDERED)) {
            throw new IllegalArgumentException("주문완료인 경우만 취소 요청이 가능합니다.");
        }
        orderStatus = OrderStatus.CANCEL;
    }

    public List<Long> getProductIds() {
        return orderProducts.stream().map(OrderProduct::getProductId).toList();
    }

    public int calculateTotalAmount() {
        return orderProducts.stream().mapToInt(OrderProduct::calculateTotalAmountWithOrderOption).sum();
    }

    public String getRepresentativeOrderedProductName() {
        if (orderProducts.size() > 1) {
            return orderProducts.get(0).getName() + " 외 " + (orderProducts.size() - 1) + "개";
        } else {
            return orderProducts.get(0).getName();
        }
    }

    public boolean isMorningOrder() {
        return orderTime.toLocalTime().isAfter(LocalTime.parse("07:59")) && orderTime.toLocalTime().isBefore(LocalTime.parse("10:01"));
    }

    public DayOfWeek getOrderDayOfWeek() {
        return orderTime.getDayOfWeek();
    }

    public void mappingPayment(Payment payment) {
        this.payment = payment;
    }

    public boolean isPayed() {
        return orderStatus.equals(OrderStatus.PAYED);
    }

    public boolean isOrderMember(Long memberId) {
        return Objects.equals(this.memberId, memberId);
    }

    private void mappingOrderProduct(OrderProduct orderProduct) {
        orderProducts.add(orderProduct);
        orderProduct.mappingOrder(this);
    }

}
