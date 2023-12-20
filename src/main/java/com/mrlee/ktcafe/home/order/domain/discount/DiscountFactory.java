package com.mrlee.ktcafe.home.order.domain.discount;

import com.mrlee.ktcafe.home.order.domain.Order;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.mrlee.ktcafe.home.order.domain.discount.DiscountPolicy.*;

@Component
public class DiscountFactory {

    private Map<DiscountPolicyName, DiscountPolicy> discountPolicyMap;

    protected DiscountFactory(Set<DiscountPolicy> discountPolicySet) {
        createStrategy(discountPolicySet);
    }

    public DiscountPolicy getDiscountPolicy(Order order) {
        if (order.isMorningOrder()) {
            return discountPolicyMap.get(DiscountPolicyName.MorningDiscount);
        } else {
            return discountPolicyMap.get(DiscountPolicyName.DayOfWeekDiscount);
        }
    }

    private void createStrategy(Set<DiscountPolicy> discountPolicySet) {
        discountPolicyMap = new HashMap<>();
        discountPolicySet.forEach(discountPolicy -> discountPolicyMap.put(discountPolicy.getDiscountPolicyName(), discountPolicy));
    }

}
