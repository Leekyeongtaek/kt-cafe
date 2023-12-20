package com.mrlee.ktcafe.home.order.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.mrlee.ktcafe.home.order.OrderFixture.anOrder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderTest {

    @Test
    void 결제완료() {
        Order order = anOrder()
                .build();

        order.payed();

        Assertions.assertThat(order.getOrderStatus()).isEqualTo(Order.OrderStatus.PAYED);
    }

    @Test
    void 주문완료가_아닌_상태에서_결제완료_상태로_변경시_예외발생() {
        Order order = anOrder()
                .orderStatus(Order.OrderStatus.CANCEL)
                .build();

        assertThatThrownBy(order::payed)
                .isInstanceOf(IllegalArgumentException.class).hasMessage("주문완료인 경우만 결제 요청이 가능합니다.");
    }

    @Test
    void 주문취소() {
        Order order = anOrder()
                .build();

        order.canceled();

        Assertions.assertThat(order.getOrderStatus()).isEqualTo(Order.OrderStatus.CANCEL);
    }

    @Test
    void 주문완료가_아닌_상태에서_주문취소_상태로_변경시_예외발생() {
        Order order = anOrder()
                .orderStatus(Order.OrderStatus.PAYED)
                .build();

        assertThatThrownBy(order::canceled)
                .isInstanceOf(IllegalArgumentException.class).hasMessage("주문완료인 경우만 취소 요청이 가능합니다.");
    }

    @Test
    void 정각_오전8시_주문은_오전할인_주문이다() {
        Order order = anOrder().orderTime(LocalDateTime.of(2023, 12, 19, 8, 0)).build();
        boolean result = order.isMorningOrder();
        assertThat(result).isTrue();
    }

    @Test
    void 정각_오전10시_주문은_오전할인_주문이다() {
        Order order = anOrder().orderTime(LocalDateTime.of(2023, 12, 19, 10, 0)).build();
        boolean result = order.isMorningOrder();
        assertThat(result).isTrue();
    }

}