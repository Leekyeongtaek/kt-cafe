package com.mrlee.ktcafe.home.order.domain.discount;

import com.mrlee.ktcafe.home.order.domain.Order;
import org.springframework.stereotype.Component;

@Component
public class MorningDiscount implements DiscountPolicy {

    @Override
    public int discountOrder(Order order) {
        int totalAmount = order.getOrderProducts().stream().mapToInt(e -> e.getPrice() * e.getCount()).sum();
        return (int) (totalAmount * DiscountRate.MORNING.getRate());
    }

    @Override
    public DiscountPolicyName getDiscountPolicyName() {
        return DiscountPolicyName.MorningDiscount;
    }
}
