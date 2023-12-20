package com.mrlee.ktcafe.home.product.repository.query.dto;

import com.mrlee.ktcafe.home.product.domain.Product;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductQueryInquiryList {

    private Long productId;
    private String name;
    private int basicPrice;
    private String description;
    private String mainImage;
    private Product.ProductType productType;

    public ProductQueryInquiryList(Long productId, String name, int basicPrice, String description, String mainImage, Product.ProductType productType) {
        this.productId = productId;
        this.name = name;
        this.basicPrice = basicPrice;
        this.description = description;
        this.mainImage = mainImage;
        this.productType = productType;
    }
}
