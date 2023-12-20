package com.mrlee.ktcafe.home.product.service.dto;

import com.mrlee.ktcafe.home.product.domain.ProductOption;
import com.mrlee.ktcafe.home.product.domain.ProductOptionGroup;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.mrlee.ktcafe.home.product.domain.Product;
import com.mrlee.ktcafe.home.product.domain.Product.ProductType;
import org.hibernate.validator.constraints.Range;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class CreateProductInput {

    @NotEmpty
    private String name;
    @Range(min = 100, max = 100000)
    private int basicPrice;
    @NotEmpty
    private String description;
    @NotEmpty
    private String mainImage;
    private String addImage1;
    private String addImage2;
    private ProductType productType;
    @Valid
    private List<CreateProductOptionGroupInput> productOptionGroupInputList = new ArrayList<>();

    @Builder
    public CreateProductInput(String name, int basicPrice, String description, String mainImage, String addImage1, String addImage2, ProductType productType, List<CreateProductOptionGroupInput> createProductOptionGroupInputList) {
        this.name = name;
        this.basicPrice = basicPrice;
        this.description = description;
        this.mainImage = mainImage;
        this.addImage1 = addImage1;
        this.addImage2 = addImage2;
        this.productType = productType;
        this.productOptionGroupInputList = createProductOptionGroupInputList;
    }

    public Product toProduct() {
        return Product.builder()
                .name(name)
                .basicPrice(basicPrice)
                .description(description)
                .mainImage(mainImage)
                .addImage1(addImage1)
                .addImage2(addImage2)
                .productType(productType)
                .productOptionGroups(productOptionGroupInputList.stream()
                        .map(CreateProductOptionGroupInput::toProductOptionGroup)
                        .toList())
                .build();
    }

    @NoArgsConstructor
    @Data
    public static class CreateProductOptionGroupInput {

        @NotEmpty
        private String name;
        @Valid
        @NotEmpty
        private List<CreateProductOptionInput> productOptionInputList = new ArrayList<>();

        @Builder
        public CreateProductOptionGroupInput(String name, List<CreateProductOptionInput> createProductOptionInputList) {
            this.name = name;
            this.productOptionInputList = createProductOptionInputList;
        }

        public ProductOptionGroup toProductOptionGroup() {
            return ProductOptionGroup.builder()
                    .name(name)
                    .productOptions(getProductOptionInputList().stream().map(CreateProductOptionInput::toProductOption).toList())
                    .build();
        }

        @NoArgsConstructor
        @Data
        public static class CreateProductOptionInput {

            @NotEmpty
            private String name;
            @Range(min = 0, max = 100000)
            private int price;

            @Builder
            public CreateProductOptionInput(String name, int price) {
                this.name = name;
                this.price = price;
            }

            public ProductOption toProductOption() {
                return ProductOption.builder()
                        .name(name)
                        .price(price)
                        .build();
            }
        }
    }
}
