package com.mrlee.ktcafe.home.product.service.dto;

import com.mrlee.ktcafe.home.product.domain.ProductOption;
import com.mrlee.ktcafe.home.product.domain.ProductOptionGroup;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class UpdateProductInput {

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
    @Valid
    private List<UpdateProductOptionGroupInput> productOptionGroupInputList = new ArrayList<>();

    @Builder
    public UpdateProductInput(String name, int basicPrice, String description, String mainImage, String addImage1, String addImage2, List<UpdateProductOptionGroupInput> productOptionGroupInputList) {
        this.name = name;
        this.basicPrice = basicPrice;
        this.description = description;
        this.mainImage = mainImage;
        this.addImage1 = addImage1;
        this.addImage2 = addImage2;
        this.productOptionGroupInputList = productOptionGroupInputList;
    }

    @Data
    @NoArgsConstructor
    public static class UpdateProductOptionGroupInput {

        private Long productOptionGroupId;
        @NotEmpty
        private String name;
        @Valid
        @NotEmpty
        private List<UpdateProductOptionInput> ProductOptionInputList = new ArrayList<>();

        @Builder
        public UpdateProductOptionGroupInput(Long productOptionGroupId, String name, List<UpdateProductOptionInput> updateProductOptionInputList) {
            this.productOptionGroupId = productOptionGroupId;
            this.name = name;
            this.ProductOptionInputList = updateProductOptionInputList;
        }

        public ProductOptionGroup toProductOptionGroup() {
            return ProductOptionGroup.builder()
                    .id(productOptionGroupId)
                    .name(name)
                    .productOptions(getProductOptionInputList().stream().map(UpdateProductOptionInput::toProductOption).toList())
                    .build();
        }

        @Data
        @NoArgsConstructor
        public static class UpdateProductOptionInput {

            private Long productOptionId;
            @NotEmpty
            private String name;
            @Range(min = 0, max = 100000)
            private int price;

            @Builder
            public UpdateProductOptionInput(Long productOptionId, String name, int price) {
                this.productOptionId = productOptionId;
                this.name = name;
                this.price = price;
            }

            public ProductOption toProductOption() {
                return ProductOption.builder()
                        .id(productOptionId)
                        .name(name)
                        .price(price)
                        .build();
            }
        }
    }
}
