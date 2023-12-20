package com.mrlee.ktcafe.home.product.domain;

import com.mrlee.ktcafe.common.BaseTime;
import com.mrlee.ktcafe.home.product.service.dto.UpdateProductInput;
import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.GenerationType.IDENTITY;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Product extends BaseTime {

    public enum ProductType {COFFEE, DRINK, BREAD, CAKE}

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "product_id")
    private Long id;
    private String name;
    private int basicPrice;
    private String description;
    private String mainImage;
    private String addImage1;
    private String addImage2;

    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @OneToMany(mappedBy = "product", cascade = ALL)
    private List<ProductOptionGroup> productOptionGroups = new ArrayList<>();

    @Builder
    public Product(Long id, String name, int basicPrice, String description, String mainImage, String addImage1, String addImage2, ProductType productType, List<ProductOptionGroup> productOptionGroups) {
        this.id = id;
        this.name = name;
        this.basicPrice = basicPrice;
        this.description = description;
        this.mainImage = mainImage;
        this.addImage1 = addImage1;
        this.addImage2 = addImage2;
        this.productType = productType;
        for (ProductOptionGroup productOptionGroup : productOptionGroups) {
            addProductOptionGroup(productOptionGroup);
        }
    }

    private void addProductOptionGroup(ProductOptionGroup productOptionGroup) {
        productOptionGroups.add(productOptionGroup);
        productOptionGroup.mappingProduct(this);
    }

    public void update(UpdateProductInput updateProductInput) {
        name = updateProductInput.getName();
        basicPrice = updateProductInput.getBasicPrice();
        description = updateProductInput.getDescription();
        mainImage = updateProductInput.getMainImage();
        addImage1 = updateProductInput.getAddImage1();
        addImage2 = updateProductInput.getAddImage2();
        for (UpdateProductInput.UpdateProductOptionGroupInput updateProductOptionGroupInput : updateProductInput.getProductOptionGroupInputList()) {
            updateProductOptionGroup(updateProductOptionGroupInput);
        }
    }

    private void updateProductOptionGroup(UpdateProductInput.UpdateProductOptionGroupInput updateProductOptionGroupInput) {

        if (updateProductOptionGroupInput.getProductOptionGroupId() == null) {
            addProductOptionGroup(updateProductOptionGroupInput.toProductOptionGroup());
            return;
        }

        for (ProductOptionGroup productOptionGroup : productOptionGroups) {
            if (productOptionGroup.getId().equals(updateProductOptionGroupInput.getProductOptionGroupId())) {
                productOptionGroup.update(updateProductOptionGroupInput.getName());
                for (UpdateProductInput.UpdateProductOptionGroupInput.UpdateProductOptionInput updateProductOptionInput : updateProductOptionGroupInput.getProductOptionInputList()) {
                    productOptionGroup.updateProductOption(updateProductOptionInput);
                }
                break;
            }
        }
    }
}
