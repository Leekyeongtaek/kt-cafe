package com.mrlee.ktcafe.home.order.service.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderSearchCond {

    private String productName;

    public OrderSearchCond(String productName) {
        this.productName = productName;
    }
}
