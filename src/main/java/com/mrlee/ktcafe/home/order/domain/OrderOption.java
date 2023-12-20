package com.mrlee.ktcafe.home.order.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class OrderOption {

    private String name;
    private int price;

    @Builder
    public OrderOption(String name, int price) {
        this.name = name;
        this.price = price;
    }
}
