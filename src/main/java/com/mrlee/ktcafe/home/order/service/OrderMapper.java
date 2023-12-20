package com.mrlee.ktcafe.home.order.service;

import com.mrlee.ktcafe.home.order.domain.Order;
import com.mrlee.ktcafe.home.order.domain.OrderOptionGroup;
import com.mrlee.ktcafe.home.order.domain.OrderProduct;
import com.mrlee.ktcafe.home.order.domain.OrderOption;
import com.mrlee.ktcafe.home.order.service.dto.ShoppingCartInput;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OrderMapper {

    public Order unpackShoppingCart(ShoppingCartInput shoppingCartInput) {
        return Order.builder()
                .memberId(shoppingCartInput.getMemberId())
                .orderTime(LocalDateTime.now())
                .orderProducts(shoppingCartInput.getCartItemInputList().stream().map(this::toOrderProduct).toList())
                .build();
    }

    private OrderProduct toOrderProduct(ShoppingCartInput.CartItemInput cartItemInput) {
        return OrderProduct.builder()
                .productId(cartItemInput.getProductId())
                .name(cartItemInput.getName())
                .price(cartItemInput.getPrice())
                .count(cartItemInput.getCount())
                .orderOptionGroups(cartItemInput.getCartOptionGroupInputList().stream().map(this::toOrderOptionGroup).toList())
                .build();
    }

    private OrderOptionGroup toOrderOptionGroup(ShoppingCartInput.CartItemInput.CartOptionGroupInput cartOptionGroupInput) {
        return OrderOptionGroup.builder()
                .name(cartOptionGroupInput.getName())
                .orderOptions(cartOptionGroupInput.getCartOptionInputList().stream().map(this::toOrderOption).toList())
                .build();
    }

    private OrderOption toOrderOption(ShoppingCartInput.CartItemInput.CartOptionInput cartOptionInput) {
        return new OrderOption(cartOptionInput.getName(), cartOptionInput.getPrice());
    }
}
