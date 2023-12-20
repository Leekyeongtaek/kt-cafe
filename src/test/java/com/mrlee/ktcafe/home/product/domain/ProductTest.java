package com.mrlee.ktcafe.home.product.domain;

import com.mrlee.ktcafe.home.product.ProductFixture;
import com.mrlee.ktcafe.home.product.service.dto.CreateProductInput;
import com.mrlee.ktcafe.home.product.service.dto.CreateProductInput.CreateProductOptionGroupInput;
import com.mrlee.ktcafe.home.product.service.dto.UpdateProductInput;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static com.mrlee.ktcafe.home.product.ProductFixture.*;
import static java.util.Arrays.asList;
import static java.util.Arrays.sort;
import static org.assertj.core.api.Assertions.*;


class ProductTest {

    @Test
    void CreateProductInput_객체로_Product_객체를_생성할_수_있다() {
        CreateProductInput createProductInput = anCreateProductInput().build();
        Product product = createProductInput.toProduct();

        assertThat(product.getId()).isNull();
        assertThat(product.getName()).isEqualTo("아메리카노(R)");
        assertThat(product.getBasicPrice()).isEqualTo(2000);
        assertThat(product.getDescription()).isEqualTo("진하게 로스팅한 커피 원두에서 느껴지는 맛");
        assertThat(product.getMainImage()).isEqualTo("mainImage");
        assertThat(product.getProductType()).isEqualTo(Product.ProductType.COFFEE);
    }

    @Test
    void CreateProductOptionGroupInput_객체로_ProductOptionGroup_객체를_생성할_수_있다() {
        CreateProductOptionGroupInput createProductOptionGroupInput = anCreateProductOptionGroupInput().build();
        ProductOptionGroup productOptionGroup = createProductOptionGroupInput.toProductOptionGroup();

        assertThat(productOptionGroup.getName()).isEqualTo("온도 및 원두선택");
        assertThat(productOptionGroup.getProductOptions().get(0).getName()).isEqualTo("온도 : HOT / 원두 : 블랙그라운드");
        assertThat(productOptionGroup.getProductOptions().get(0).getPrice()).isEqualTo(0);
    }

    @Test
    void UpdateProductInput_객체로_Product_객체를_수정할_수_있다() {
        UpdateProductInput updateProductInput = anUpdateProductInput().build();
        Product product = anProduct().build();

        product.update(updateProductInput);

        assertThat(product.getName()).isEqualTo("아메리카노(R) 변경");
        assertThat(product.getBasicPrice()).isEqualTo(3000);
        assertThat(product.getDescription()).isEqualTo("진하게 로스팅한 커피 원두에서 느껴지는 맛 변경");
        assertThat(product.getMainImage()).isEqualTo("mainImage 변경");
    }

    @Test
    void UpdateProductInput_객체로_ProductOptionGroup_객체를_수정할_수_있다() {
        Product product = anProduct().build();

        UpdateProductInput updateProductInput = anUpdateProductInput()
                .productOptionGroupInputList(asList(
                        anUpdateProductOptionGroupInput().updateProductOptionInputList(
                                asList(
                                        anUpdateProductOptionInput().name("온도 : HOT / 원두 : 블랙그라운드 변경").price(500).build(),
                                        anUpdateProductOptionInput().productOptionId(null).name("온도 : ICE / 원두 : 블랙그라운드").price(0).build())).build(),
                        anUpdateProductOptionGroupInput().productOptionGroupId(null).name("추가선택").updateProductOptionInputList(
                                Collections.singletonList(
                                        anUpdateProductOptionInput().productOptionId(null).name("샷추가").price(500).build())).build())
                ).build();

        product.update(updateProductInput);

        assertThat(product.getProductOptionGroups().get(0).getName()).isEqualTo("온도 및 원두선택 변경");
        assertThat(product.getProductOptionGroups().get(0).getProductOptions().get(0).getName()).isEqualTo("온도 : HOT / 원두 : 블랙그라운드 변경");
        assertThat(product.getProductOptionGroups().get(0).getProductOptions().get(0).getPrice()).isEqualTo(500);
        assertThat(product.getProductOptionGroups().get(0).getProductOptions().get(1).getName()).isEqualTo("온도 : ICE / 원두 : 블랙그라운드");
        assertThat(product.getProductOptionGroups().get(0).getProductOptions().get(1).getPrice()).isEqualTo(0);
        assertThat(product.getProductOptionGroups().get(1).getName()).isEqualTo("추가선택");
        assertThat(product.getProductOptionGroups().get(1).getProductOptions().get(0).getName()).isEqualTo("샷추가");
        assertThat(product.getProductOptionGroups().get(1).getProductOptions().get(0).getPrice()).isEqualTo(500);
    }
}