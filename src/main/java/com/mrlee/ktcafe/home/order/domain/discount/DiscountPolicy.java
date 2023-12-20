package com.mrlee.ktcafe.home.order.domain.discount;

import com.mrlee.ktcafe.home.order.domain.Order;
import lombok.Getter;

public interface DiscountPolicy {

    int discountOrder(Order order);

    DiscountPolicyName getDiscountPolicyName();

    enum DiscountPolicyName {
        DayOfWeekDiscount, MorningDiscount
    }

    @Getter
    enum DiscountRate {
        MORNING(0.2), COFFEE(0.1), CAKE(0.05), DRINK(0.05), BREAD(0.07);

        private final double rate;

        DiscountRate(double rate) {
            this.rate = rate;
        }
    }
}
