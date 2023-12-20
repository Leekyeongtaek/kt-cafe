package com.mrlee.ktcafe.home.product.repository.query;

import com.mrlee.ktcafe.home.product.domain.QProduct;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import com.mrlee.ktcafe.home.product.service.dto.ProductSearchCond;
import com.mrlee.ktcafe.home.product.domain.Product.ProductType;
import com.mrlee.ktcafe.home.product.repository.query.dto.ProductQueryInquiryList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

import static com.mrlee.ktcafe.home.product.domain.QProduct.product;


@RequiredArgsConstructor
@Repository
public class ProductQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Page<ProductQueryInquiryList> searchProduct(Pageable pageable, ProductSearchCond productSearchCond) {

        List<ProductQueryInquiryList> content = queryFactory
                .select(Projections.constructor(ProductQueryInquiryList.class,
                        product.id, product.name, product.basicPrice, product.description, product.mainImage, product.productType
                ))
                .from(product)
                .where(
                        nameEq(productSearchCond.getName()),
                        productTypeEq(productSearchCond.getProductType()),
                        basicPriceGoe(productSearchCond.getMinPrice()),
                        basicPriceLoe(productSearchCond.getMaxPrice()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(product.id.desc())
                .fetch();

        Long totalCount = queryFactory
                .select(product.count())
                .from(product)
                .where(
                        nameEq(productSearchCond.getName()),
                        productTypeEq(productSearchCond.getProductType()),
                        basicPriceGoe(productSearchCond.getMinPrice()),
                        basicPriceLoe(productSearchCond.getMaxPrice())
                ).fetchOne();

        return new PageImpl<>(content, pageable, totalCount);
    }

    private BooleanExpression nameEq(String name) {
        return StringUtils.hasText(name) ? product.name.contains(name) : null;
    }

    private BooleanExpression basicPriceGoe(Integer minPrice) {
        return minPrice != null ? product.basicPrice.goe(minPrice) : null;
    }

    private BooleanExpression basicPriceLoe(Integer maxPrice) {
        return maxPrice != null ? product.basicPrice.loe(maxPrice) : null;
    }

    private BooleanExpression productTypeEq(ProductType productType) {
        return Objects.nonNull(productType) ? product.productType.eq(productType) : null;
    }
}
