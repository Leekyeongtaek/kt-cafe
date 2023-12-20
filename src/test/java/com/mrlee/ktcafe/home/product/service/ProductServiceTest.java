package com.mrlee.ktcafe.home.product.service;

import com.mrlee.ktcafe.home.member.repository.MemberRepository;
import com.mrlee.ktcafe.home.product.domain.Product;
import com.mrlee.ktcafe.home.product.repository.ProductRepository;
import com.mrlee.ktcafe.home.product.repository.query.dto.ProductQueryInquiryList;
import com.mrlee.ktcafe.home.product.service.dto.CreateProductInput;
import com.mrlee.ktcafe.home.product.service.dto.ProductInquiry;
import com.mrlee.ktcafe.home.product.service.dto.ProductSearchCond;
import com.mrlee.ktcafe.home.product.service.dto.UpdateProductInput;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static com.mrlee.ktcafe.home.product.ProductFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@Sql(value = "/db/sql/product/product.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Transactional
@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 등록시_상품명은_중복될_수_없다() {
        CreateProductInput createProductInput = anCreateProductInput().build();

        assertThatThrownBy(() -> productService.addProduct(createProductInput))
                .isInstanceOf(IllegalArgumentException.class).hasMessage("이미 존재하는 상품 이름 또는 상품옵션그룹 이름입니다.");
    }

    @Test
    void 수정시_옵션그룹명은_중복될_수_없다() {
        UpdateProductInput updateProductInput = anUpdateProductInput().productOptionGroupInputList(
                        Collections.singletonList(anUpdateProductOptionGroupInput().productOptionGroupId(null).name("온도 및 원두선택").updateProductOptionInputList(
                                Collections.singletonList(anUpdateProductOptionInput().productOptionId(null).build())).build()))
                .build();

        assertThatThrownBy(() -> {
            productService.modifyProduct(1L, updateProductInput);
            em.flush();
        }).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void 상세조회() {
        ProductInquiry productInquiry = productService.findProduct(1L);

        assertThat(productInquiry.getProductId()).isEqualTo(1L);
        assertThat(productInquiry.getName()).isEqualTo("아메리카노(R)");
        assertThat(productInquiry.getBasicPrice()).isEqualTo(2000);
    }

    @Test
    void 목록검색() {
        ProductSearchCond productSearchCond = ProductSearchCond.builder().name("카페모카")
                .minPrice(3000)
                .maxPrice(4000)
                .productType(Product.ProductType.COFFEE)
                .build();

        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<ProductQueryInquiryList> resultPage = productService.searchProduct(pageRequest, productSearchCond);

        List<ProductQueryInquiryList> result = resultPage.getContent();

        assertThat(result).extracting("name").containsExactly("카페모카(R)");
    }

    @Test
    void 옵션그룹_삭제() {
        Long productOptionGroupId = 1L;

        productService.removeProductOptionGroup(productOptionGroupId);
        em.flush();

        Product product = productRepository.findById(1L).get();

        assertThat(product.getProductOptionGroups().size()).isEqualTo(0);
    }

    @Test
    void 옵션그룹은_옵션을_1개_이하로_삭제할_수_없다() {
        Long productOptionGroupId = 1L;
        Long productOptionId = 1L;

        assertThatThrownBy(() -> productService.removeProductOption(productOptionGroupId, productOptionId))
                .isInstanceOf(IllegalArgumentException.class).hasMessage("상품 옵션 그룹은 상품 옵션을 1개 이하로 삭제할 수 없습니다.");
    }

    @Test
    void 옵션삭제() {
        Long productOptionGroupId = 2L;
        Long productOptionId = 3L;

        productService.removeProductOption(productOptionGroupId, productOptionId);
        em.flush();

        Product product = productRepository.findById(2L).get();

        assertThat(product.getProductOptionGroups().get(0).getProductOptions().size()).isEqualTo(1);
    }
}