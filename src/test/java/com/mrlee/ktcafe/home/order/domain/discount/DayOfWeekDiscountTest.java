package com.mrlee.ktcafe.home.order.domain.discount;

import com.mrlee.ktcafe.home.order.domain.Order;
import com.mrlee.ktcafe.home.product.domain.Product;
import com.mrlee.ktcafe.home.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;

import static com.mrlee.ktcafe.home.order.OrderFixture.anOrder;
import static com.mrlee.ktcafe.home.order.OrderFixture.anOrderProduct;
import static com.mrlee.ktcafe.home.product.ProductFixture.anProduct;
import static java.time.DayOfWeek.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class DayOfWeekDiscountTest {

    @Autowired
    private DayOfWeekDiscount dayOfWeekDiscount;

    @BeforeEach
    void setUp() {
        dayOfWeekDiscount = new DayOfWeekDiscount(mock(ProductRepository.class));
    }

    @Test
    void 월요일_주문은_커피상품의_주문금액을_10프로_할인해준다() {
        Order order = anOrder()
                .orderTime(LocalDateTime.of(2023, 12, 18, 12, 0))
                .orderProducts(Collections.singletonList(
                        anOrderProduct()
                                .id(1L)
                                .name("아메리카노(R)")
                                .price(4500)
                                .count(4)
                                .orderOptionGroups(Collections.emptyList())
                                .build()
                ))
                .build();

        Product product = anProduct()
                .id(1L)
                .productType(Product.ProductType.COFFEE)
                .build();

        int discountAmount = dayOfWeekDiscount.discountOrder(order, new HashMap<>() {{
            put(1L, product);
        }});

        assertThat(order.getOrderDayOfWeek()).isEqualTo(MONDAY);
        assertThat(discountAmount).isEqualTo(1800);
    }

    @Test
    void 화요일_주문은_케이크상품의_주문금액을_5프로_할인해준다() {
        Order order = anOrder()
                .orderTime(LocalDateTime.of(2023, 12, 19, 12, 0))
                .orderProducts(Collections.singletonList(
                        anOrderProduct()
                                .id(1L)
                                .name("떠먹는티라미수")
                                .price(6500)
                                .count(2)
                                .orderOptionGroups(Collections.emptyList())
                                .build()
                ))
                .build();

        Product product = anProduct()
                .id(1L)
                .productType(Product.ProductType.CAKE)
                .build();

        int discountAmount = dayOfWeekDiscount.discountOrder(order, new HashMap<>() {{
            put(1L, product);
        }});

        assertThat(order.getOrderDayOfWeek()).isEqualTo(TUESDAY);
        assertThat(discountAmount).isEqualTo(650);
    }

    @Test
    void 수요일_주문은_음료상품의_주문금액을_5프로_할인해준다() {
        Order order = anOrder()
                .orderTime(LocalDateTime.of(2023, 12, 20, 12, 0))
                .orderProducts(Collections.singletonList(
                        anOrderProduct()
                                .id(1L)
                                .name("요거트프라페(R)")
                                .price(5800)
                                .count(2)
                                .orderOptionGroups(Collections.emptyList())
                                .build()
                ))
                .build();

        Product product = anProduct()
                .id(1L)
                .productType(Product.ProductType.DRINK)
                .build();

        int discountAmount = dayOfWeekDiscount.discountOrder(order, new HashMap<>() {{
            put(1L, product);
        }});

        assertThat(order.getOrderDayOfWeek()).isEqualTo(WEDNESDAY);
        assertThat(discountAmount).isEqualTo(580);
    }

    @Test
    void 목요일_주문은_빵상품의_주문금액을_7프로_할인해준다() {
        Order order = anOrder()
                .orderTime(LocalDateTime.of(2023, 12, 21, 12, 0))
                .orderProducts(Collections.singletonList(
                        anOrderProduct()
                                .id(1L)
                                .name("슈톨렌 바통 휘낭시에")
                                .price(2700)
                                .count(2)
                                .orderOptionGroups(Collections.emptyList())
                                .build()
                ))
                .build();

        Product product = anProduct()
                .id(1L)
                .productType(Product.ProductType.BREAD)
                .build();

        int discountAmount = dayOfWeekDiscount.discountOrder(order, new HashMap<>() {{
            put(1L, product);
        }});

        assertThat(order.getOrderDayOfWeek()).isEqualTo(THURSDAY);
        assertThat(discountAmount).isEqualTo(378);
    }

    @Test
    void 금요일_주문은_커피상품의_주문금액을_10프로_할인해준다() {
        Order order = anOrder()
                .orderTime(LocalDateTime.of(2023, 12, 22, 12, 0))
                .orderProducts(Collections.singletonList(
                        anOrderProduct()
                                .id(1L)
                                .name("카페모카(R)")
                                .price(5500)
                                .count(2)
                                .orderOptionGroups(Collections.emptyList())
                                .build()
                ))
                .build();

        Product product = anProduct()
                .id(1L)
                .productType(Product.ProductType.COFFEE)
                .build();

        int discountAmount = dayOfWeekDiscount.discountOrder(order, new HashMap<>() {{
            put(1L, product);
        }});

        assertThat(order.getOrderDayOfWeek()).isEqualTo(FRIDAY);
        assertThat(discountAmount).isEqualTo(1100);
    }

    @Test
    void 토요일_주문은_할인되지_않는다() {
        Order order = anOrder()
                .orderTime(LocalDateTime.of(2023, 12, 23, 12, 0))
                .orderProducts(Collections.singletonList(
                        anOrderProduct()
                                .id(1L)
                                .name("카페모카(R)")
                                .price(5500)
                                .count(2)
                                .orderOptionGroups(Collections.emptyList())
                                .build()
                ))
                .build();

        Product product = anProduct()
                .id(1L)
                .productType(Product.ProductType.COFFEE)
                .build();

        int discountAmount = dayOfWeekDiscount.discountOrder(order, new HashMap<>() {{
            put(1L, product);
        }});

        assertThat(order.getOrderDayOfWeek()).isEqualTo(SATURDAY);
        assertThat(discountAmount).isEqualTo(0);
    }

    @Test
    void 일요일_주문은_할인되지_않는다() {
        Order order = anOrder()
                .orderTime(LocalDateTime.of(2023, 12, 24, 12, 0))
                .orderProducts(Collections.singletonList(
                        anOrderProduct()
                                .id(1L)
                                .name("카페모카(R)")
                                .price(5500)
                                .count(2)
                                .orderOptionGroups(Collections.emptyList())
                                .build()
                ))
                .build();

        Product product = anProduct()
                .id(1L)
                .productType(Product.ProductType.COFFEE)
                .build();

        int discountAmount = dayOfWeekDiscount.discountOrder(order, new HashMap<>() {{
            put(1L, product);
        }});

        assertThat(order.getOrderDayOfWeek()).isEqualTo(SUNDAY);
        assertThat(discountAmount).isEqualTo(0);
    }
}