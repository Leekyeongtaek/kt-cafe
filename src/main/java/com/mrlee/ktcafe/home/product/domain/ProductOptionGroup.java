package com.mrlee.ktcafe.home.product.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.mrlee.ktcafe.common.BaseTime;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;
import static com.mrlee.ktcafe.home.product.service.dto.UpdateProductInput.UpdateProductOptionGroupInput.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class ProductOptionGroup extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_option_group_id")
    private Long id;
    private String name;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(mappedBy = "productOptionGroup", cascade = ALL)
    private List<ProductOption> productOptions = new ArrayList<>();

    @Builder
    public ProductOptionGroup(Long id, String name, List<ProductOption> productOptions) {
        this.id = id;
        this.name = name;
        for (ProductOption productOption : productOptions) {
            addProductOption(productOption);
        }
    }

    public void update(String name) {
        this.name = name;
    }

    void updateProductOption(UpdateProductOptionInput updateProductOptionInput) {

        if (updateProductOptionInput.getProductOptionId() == null) {
            addProductOption(updateProductOptionInput.toProductOption());
            return;
        }

        for (ProductOption productOption : productOptions) {
            if (productOption.getId().equals(updateProductOptionInput.getProductOptionId())) {
                productOption.update(updateProductOptionInput.getName(), updateProductOptionInput.getPrice());
                break;
            }
        }
    }

    public void addProductOption(ProductOption productOption) {
        this.productOptions.add(productOption);
        productOption.mappingProductOptionGroup(this);
    }

    void mappingProduct(Product product) {
        this.product = product;
    }

}
