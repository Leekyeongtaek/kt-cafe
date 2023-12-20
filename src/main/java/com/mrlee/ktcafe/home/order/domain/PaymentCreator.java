package com.mrlee.ktcafe.home.order.domain;

import com.mrlee.ktcafe.home.order.domain.discount.DiscountFactory;
import com.mrlee.ktcafe.home.order.domain.discount.DiscountPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 결제 정보 생성을 도와주는 컴포넌트
 * 할인 정책 요청
 * 추후 쿠폰 사용, 멤버십 사용 프로세스 추가 예정.
 */
@RequiredArgsConstructor
@Component
public class PaymentCreator {

    private final DiscountFactory discountFactory;

    public Payment createPayment(Order order, Payment.PaymentMethod paymentMethod) {

        DiscountPolicy discountPolicy = discountFactory.getDiscountPolicy(order);
        int discountAmount = discountPolicy.discountOrder(order);

        return Payment.builder()
                .totalAmount(order.calculateTotalAmount())
                .paymentAmount(order.calculateTotalAmount() - discountAmount)
                .discountAmount(discountAmount)
                .paymentMethod(paymentMethod)
                .build();
    }
}
