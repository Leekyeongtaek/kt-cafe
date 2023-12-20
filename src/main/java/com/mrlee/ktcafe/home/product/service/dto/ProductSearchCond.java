package com.mrlee.ktcafe.home.product.service.dto;

import com.mrlee.ktcafe.home.product.domain.Product;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class ProductSearchCond {

    private String name;
    private Integer minPrice;
    private Integer maxPrice;
    private Product.ProductType productType;

    @Builder
    public ProductSearchCond(String name, Integer minPrice, Integer maxPrice, Product.ProductType productType) {
        this.name = name;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.productType = productType;
    }
}
