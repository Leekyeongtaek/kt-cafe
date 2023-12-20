package com.mrlee.ktcafe.home.order;

import com.mrlee.ktcafe.home.order.domain.*;
import com.mrlee.ktcafe.home.order.service.dto.CreateReviewInput;
import com.mrlee.ktcafe.home.order.service.dto.UpdateReviewInput;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.util.Arrays.asList;

public class OrderFixture {

    public static Order.OrderBuilder anOrder() {
        return Order.builder()
                .id(1L)
                .memberId(1L)
                .payment(anPayment().build())
                .orderStatus(Order.OrderStatus.ORDERED)
                .orderTime(LocalDateTime.now())
                .orderProducts(asList(anOrderProduct().build()));
    }

    public static OrderProduct.OrderProductBuilder anOrderProduct() {
        return OrderProduct.builder()
                .id(1L)
                .productId(1L)
                .name("아메리카노(R)")
                .price(2000)
                .count(1)
                .orderOptionGroups(asList(anOrderOptionGroup().build()));
    }

    public static OrderOptionGroup.OrderOptionGroupBuilder anOrderOptionGroup() {
        return OrderOptionGroup.builder()
                .name("온도 및 원두선택")
                .orderOptions(asList(anOrderOption().build()));
    }

    public static OrderOption.OrderOptionBuilder anOrderOption() {
        return OrderOption.builder()
                .name("온도 : HOT / 원두 : 블랙그라운드")
                .price(0);
    }

    public static Payment.PaymentBuilder anPayment() {
        return Payment.builder()
                .id(1L)
                .totalAmount(2000)
                .paymentAmount(2000)
                .discountAmount(0)
                .paymentMethod(Payment.PaymentMethod.CARD);
    }

    public static CreateReviewInput.CreateReviewInputBuilder anCreateReviewInput() {
        return CreateReviewInput.builder()
                .orderId(1L)
                .memberId(1L)
                .rating(3)
                .contents("맛있어요!!")
                .photoImage("www.photo.com");
    }

    public static UpdateReviewInput.UpdateReviewInputBuilder anUpdateReviewInput() {
        return UpdateReviewInput.builder()
                .rating(4)
                .contents("맛있어요!! 변경")
                .photoImage("www.photo.com 변경");
    }

    public static Review.ReviewBuilder anReview() {
        return Review.builder()
                .id(1L)
                .rating(3)
                .contents("맛있어요!!")
                .photoImage("www.photo.com");
    }
}
