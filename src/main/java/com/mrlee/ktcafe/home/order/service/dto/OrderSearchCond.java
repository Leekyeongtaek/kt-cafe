package com.mrlee.ktcafe.home.order.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderSearchCond {

    private String productName;

    public OrderSearchCond(String productName) {
        this.productName = productName;
    }
}
