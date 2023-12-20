package com.mrlee.ktcafe.home.product.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.mrlee.ktcafe.common.BaseTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class ProductOption extends BaseTime {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "product_option_id")
    private Long id;
    private String name;
    private int price;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_option_group_id")
    private ProductOptionGroup productOptionGroup;

    @Builder
    public ProductOption(Long id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public void update(String name, int price) {
        this.name = name;
        this.price = price;
    }

    void mappingProductOptionGroup(ProductOptionGroup productOptionGroup) {
        this.productOptionGroup = productOptionGroup;
    }
}
