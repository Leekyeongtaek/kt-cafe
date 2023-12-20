package com.mrlee.ktcafe.home.order.domain;

import com.mrlee.ktcafe.home.product.domain.Product;
import com.mrlee.ktcafe.home.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import static com.mrlee.ktcafe.home.order.OrderFixture.*;
import static com.mrlee.ktcafe.home.product.ProductFixture.anProduct;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

class OrderValidatorTest {

    private OrderValidator orderValidator;

    @BeforeEach
    void setUp() {
        orderValidator = new OrderValidator(mock(ProductRepository.class));
    }

    @Test
    void 주문_검증_성공() {
        Product product = anProduct().build();
        Order order = anOrder().build();

        orderValidator.validate(order, new HashMap<>() {{
            put(1L, product);
        }});
    }

    @Test
    void 주문상품_목록이_비어있는_경우() {
        Order order = anOrder().orderProducts(Collections.emptyList()).build();

        assertThatThrownBy(() ->
                orderValidator.validate(order, new HashMap<>())
        ).isInstanceOf(IllegalArgumentException.class).hasMessage("주문 상품 목록이 비어있습니다.");
    }

    @Test
    void 주문상품_상품명이_변경된_경우() {
        Product product = anProduct().build();

        Order order = anOrder().orderProducts(Arrays.asList(anOrderProduct().name("아메리카노(R) 변경").build())).build();

        assertThatThrownBy(() -> orderValidator.validate(order, new HashMap<>() {{
            put(1L, product);
        }})).isInstanceOf(IllegalArgumentException.class).hasMessage("상품명이 변경됐습니다.");
    }

    @Test
    void 존재하지_않는_상품인_경우() {
        Product product = anProduct().build();

        Order order = anOrder().orderProducts(
                        Collections.singletonList(anOrderProduct().productId(null).build()))
                .build();

        assertThatThrownBy(() -> orderValidator.validate(order, new HashMap<>() {{
            put(1L, product);
        }})).isInstanceOf(IllegalArgumentException.class).hasMessage("존재하지 않는 상품입니다.");
    }

    @Test
    void 주문상품_상품가격이_변경된_경우() {
        Product product = anProduct().build();

        Order order = anOrder().orderProducts(Collections.singletonList(anOrderProduct().price(5000).build())).build();

        assertThatThrownBy(() -> orderValidator.validate(order, new HashMap<>() {{
            put(1L, product);
        }})).isInstanceOf(IllegalArgumentException.class).hasMessage("상품가격이 변경됐습니다.");
    }

    @Test
    void 주문상품_옵션그룹명이_변경된_경우() {
        Product product = anProduct().build();

        Order order = anOrder().orderProducts(
                Collections.singletonList(anOrderProduct().orderOptionGroups(
                        Collections.singletonList(anOrderOptionGroup().name("온도 및 원두선택 변경")
                                .build())).build())).build();

        assertThatThrownBy(() -> orderValidator.validate(order, new HashMap<>() {{
            put(1L, product);
        }})).isInstanceOf(IllegalArgumentException.class).hasMessage("옵션그룹명이 변경됐습니다.");
    }

    @Test
    void 주문상품_옵션명이_변경된_경우() {
        Product product = anProduct().build();

        Order order = anOrder().orderProducts(
                Collections.singletonList(anOrderProduct().orderOptionGroups(
                        Collections.singletonList(anOrderOptionGroup().orderOptions(
                                Collections.singletonList(anOrderOption().name("온도 : HOT / 원두 : 블랙그라운드 변경")
                                        .build())).build())).build())).build();

        assertThatThrownBy(() -> orderValidator.validate(order, new HashMap<>() {{
            put(1L, product);
        }})).isInstanceOf(IllegalArgumentException.class).hasMessage("옵션명이 변경됐습니다.");
    }

    @Test
    void 주문상품_옵션가격이_변경된_경우() {
        Product product = anProduct().build();

        Order order = anOrder().orderProducts(
                Collections.singletonList(anOrderProduct().orderOptionGroups(
                        Collections.singletonList(anOrderOptionGroup().orderOptions(
                                Collections.singletonList(anOrderOption().price(500)
                                        .build())).build())).build())).build();

        assertThatThrownBy(() -> orderValidator.validate(order, new HashMap<>() {{
            put(1L, product);
        }})).isInstanceOf(IllegalArgumentException.class).hasMessage("옵션 가격이 변경됐습니다.");
    }
}