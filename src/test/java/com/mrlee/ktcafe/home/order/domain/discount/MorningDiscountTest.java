package com.mrlee.ktcafe.home.order.domain.discount;

import com.mrlee.ktcafe.home.order.domain.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static com.mrlee.ktcafe.home.order.OrderFixture.*;
import static org.assertj.core.api.Assertions.assertThat;

class MorningDiscountTest {

    private MorningDiscount morningDiscount;

    @BeforeEach
    void setUp() {
        morningDiscount = new MorningDiscount();
    }

    @Test
    void 오전할인은_주문_상품의_옵션을_제외하고_전체_금액의_20프로를_할인해준다() {
        Order order = anOrder()
                .orderProducts(
                        Arrays.asList(
                                anOrderProduct()
                                        .name("아메리카노(R)")
                                        .price(4500).count(4)
                                        .orderOptionGroups(Collections.singletonList(
                                                anOrderOptionGroup().name("추가선택")
                                                        .orderOptions(Collections.singletonList(
                                                                anOrderOption().name("샷추가").price(500).build()
                                                        )).build()
                                        )).build(),
                                anOrderProduct()
                                        .name("카페라떼(R)")
                                        .price(5000)
                                        .count(2)
                                        .orderOptionGroups(Collections.emptyList())
                                        .build()
                        )).build();

        int discountAmount = morningDiscount.discountOrder(order);

        assertThat(discountAmount).isEqualTo(5600);
    }
}