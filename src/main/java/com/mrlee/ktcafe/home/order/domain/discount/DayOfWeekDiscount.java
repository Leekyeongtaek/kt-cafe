package com.mrlee.ktcafe.home.order.domain.discount;

import com.mrlee.ktcafe.home.order.domain.Order;
import com.mrlee.ktcafe.home.order.domain.OrderProduct;
import com.mrlee.ktcafe.home.product.domain.Product;
import com.mrlee.ktcafe.home.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.mrlee.ktcafe.home.order.domain.discount.DiscountPolicy.DiscountRate.*;
import static java.time.DayOfWeek.*;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@RequiredArgsConstructor
@Component
public class DayOfWeekDiscount implements DiscountPolicy {

    private final ProductRepository productRepository;

    @Override
    public int discountOrder(Order order) {
        Map<Long, Product> productMap = getProducts(order);
        return discountOrder(order, productMap);
    }

    int discountOrder(Order order, Map<Long, Product> productMap) {
        int discountAmount = 0;

        for (OrderProduct orderProduct : order.getOrderProducts()) {
            Product product = productMap.get(orderProduct.getProductId());
            if (order.getOrderDayOfWeek().equals(MONDAY) || order.getOrderDayOfWeek().equals(FRIDAY) && product.getProductType().equals(Product.ProductType.COFFEE)) {
                discountAmount += calDiscountPrice(orderProduct, COFFEE);
            } else if (order.getOrderDayOfWeek().equals(TUESDAY) && product.getProductType().equals(Product.ProductType.CAKE)) {
                discountAmount += calDiscountPrice(orderProduct, CAKE);
            } else if (order.getOrderDayOfWeek().equals(WEDNESDAY) && product.getProductType().equals(Product.ProductType.DRINK)) {
                discountAmount += calDiscountPrice(orderProduct, DRINK);
            } else if (order.getOrderDayOfWeek().equals(THURSDAY) && product.getProductType().equals(Product.ProductType.BREAD)) {
                discountAmount += calDiscountPrice(orderProduct, BREAD);
            }
        }

        return discountAmount;
    }

    @Override
    public DiscountPolicyName getDiscountPolicyName() {
        return DiscountPolicyName.DayOfWeekDiscount;
    }

    private Map<Long, Product> getProducts(Order order) {
        return productRepository.findAllById(order.getProductIds()).stream().collect(toMap(Product::getId, identity()));
    }

    private int calDiscountPrice(OrderProduct orderProduct, DiscountRate discountRate) {
        return (int) (orderProduct.calculateTotalAmount() * discountRate.getRate());
    }
}
