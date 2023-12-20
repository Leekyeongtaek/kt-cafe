package com.mrlee.ktcafe.home.product;

import com.mrlee.ktcafe.home.product.domain.Product;
import com.mrlee.ktcafe.home.product.domain.Product.ProductBuilder;
import com.mrlee.ktcafe.home.product.domain.ProductOption;
import com.mrlee.ktcafe.home.product.domain.ProductOption.ProductOptionBuilder;
import com.mrlee.ktcafe.home.product.domain.ProductOptionGroup;
import com.mrlee.ktcafe.home.product.domain.ProductOptionGroup.ProductOptionGroupBuilder;
import com.mrlee.ktcafe.home.product.service.dto.CreateProductInput;
import com.mrlee.ktcafe.home.product.service.dto.CreateProductInput.CreateProductInputBuilder;
import com.mrlee.ktcafe.home.product.service.dto.CreateProductInput.CreateProductOptionGroupInput;
import com.mrlee.ktcafe.home.product.service.dto.CreateProductInput.CreateProductOptionGroupInput.CreateProductOptionInput;
import com.mrlee.ktcafe.home.product.service.dto.UpdateProductInput;
import com.mrlee.ktcafe.home.product.service.dto.UpdateProductInput.UpdateProductInputBuilder;
import com.mrlee.ktcafe.home.product.service.dto.UpdateProductInput.UpdateProductOptionGroupInput;
import com.mrlee.ktcafe.home.product.service.dto.UpdateProductInput.UpdateProductOptionGroupInput.UpdateProductOptionGroupInputBuilder;
import com.mrlee.ktcafe.home.product.service.dto.UpdateProductInput.UpdateProductOptionGroupInput.UpdateProductOptionInput;
import com.mrlee.ktcafe.home.product.service.dto.UpdateProductInput.UpdateProductOptionGroupInput.UpdateProductOptionInput.UpdateProductOptionInputBuilder;

import java.util.Collections;

import static java.util.Arrays.asList;

public class ProductFixture {

    public static CreateProductInputBuilder anCreateProductInput() {
        return CreateProductInput.builder()
                .name("아메리카노(R)")
                .basicPrice(2000)
                .description("진하게 로스팅한 커피 원두에서 느껴지는 맛")
                .mainImage("mainImage")
                .productType(Product.ProductType.COFFEE)
                .createProductOptionGroupInputList(Collections.singletonList(anCreateProductOptionGroupInput().build()));
    }

    public static CreateProductOptionGroupInput.CreateProductOptionGroupInputBuilder anCreateProductOptionGroupInput() {
        return CreateProductOptionGroupInput.builder()
                .name("온도 및 원두선택")
                .createProductOptionInputList(Collections.singletonList(anCreateProductOptionInput().build()));
    }

    public static CreateProductOptionInput.CreateProductOptionInputBuilder anCreateProductOptionInput() {
        return CreateProductOptionInput.builder()
                .name("온도 : HOT / 원두 : 블랙그라운드")
                .price(0);
    }

    public static UpdateProductInputBuilder anUpdateProductInput() {
        return UpdateProductInput.builder()
                .name("아메리카노(R) 변경")
                .basicPrice(3000)
                .description("진하게 로스팅한 커피 원두에서 느껴지는 맛 변경")
                .mainImage("mainImage 변경")
                .productOptionGroupInputList(Collections.singletonList(anUpdateProductOptionGroupInput().build()));
    }

    public static UpdateProductOptionGroupInputBuilder anUpdateProductOptionGroupInput() {
        return UpdateProductOptionGroupInput.builder()
                .productOptionGroupId(1L)
                .name("온도 및 원두선택 변경")
                .updateProductOptionInputList(Collections.singletonList(anUpdateProductOptionInput().build()));
    }

    public static UpdateProductOptionInputBuilder anUpdateProductOptionInput() {
        return UpdateProductOptionInput.builder()
                .productOptionId(1L)
                .name("온도 : HOT / 원두 : 블랙그라운드 변경")
                .price(500);
    }

    public static ProductBuilder anProduct() {
        return Product.builder()
                .id(1L)
                .name("아메리카노(R)")
                .basicPrice(2000)
                .description("진하게 로스팅한 커피 원두에서 느껴지는 맛")
                .mainImage("mainImage")
                .productType(Product.ProductType.COFFEE)
                .productOptionGroups(asList(anProductOptionGroup().build()));
    }

    public static ProductOptionGroupBuilder anProductOptionGroup() {
        return ProductOptionGroup.builder()
                .id(1L)
                .name("온도 및 원두선택")
                .productOptions(Collections.singletonList(anProductOption().build()));
    }

    public static ProductOptionBuilder anProductOption() {
        return ProductOption.builder()
                .id(1L)
                .name("온도 : HOT / 원두 : 블랙그라운드")
                .price(0);
    }

}
