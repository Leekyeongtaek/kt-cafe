package com.mrlee.ktcafe.home.order.domain;

import com.mrlee.ktcafe.common.BaseTime;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Payment extends BaseTime {

    public enum PaymentMethod {CARD}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;
    private int totalAmount;
    private int discountAmount;
    private int paymentAmount;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Builder
    public Payment(Long id, int totalAmount, int discountAmount, int paymentAmount, PaymentMethod paymentMethod) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.discountAmount = discountAmount;
        this.paymentAmount = paymentAmount;
        this.paymentMethod = paymentMethod;
    }
}
