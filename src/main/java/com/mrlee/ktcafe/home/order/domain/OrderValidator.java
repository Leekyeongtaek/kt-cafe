package com.mrlee.ktcafe.home.order.domain;

import com.mrlee.ktcafe.home.product.domain.ProductOption;
import com.mrlee.ktcafe.home.product.domain.ProductOptionGroup;
import com.mrlee.ktcafe.home.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import com.mrlee.ktcafe.home.product.domain.Product;
import org.springframework.stereotype.Component;

import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@RequiredArgsConstructor
@Component
public class OrderValidator {

    private final ProductRepository productRepository;

    //todo 동일한 상품 목록 체크 추가 필요.
    public void validate(Order order) {
        validate(order, getProducts(order));
    }

    void validate(Order order, Map<Long, Product> productMap) {

        if (order.getOrderProducts().isEmpty()) {
            throw new IllegalArgumentException("주문 상품 목록이 비어있습니다.");
        }

        for (OrderProduct orderProduct : order.getOrderProducts()) {
            validateOrderProduct(orderProduct, productMap.get(orderProduct.getProductId()));
        }
    }

    private void validateOrderProduct(OrderProduct orderProduct, Product product) {

        if (product == null) {
            throw new IllegalArgumentException("존재하지 않는 상품입니다.");
        }

        if (!product.getName().equals(orderProduct.getName())) {
            throw new IllegalArgumentException("상품명이 변경됐습니다.");
        }

        if (product.getBasicPrice() != orderProduct.getPrice()) {
            throw new IllegalArgumentException("상품가격이 변경됐습니다.");
        }

        for (OrderOptionGroup orderOptionGroup : orderProduct.getOrderOptionGroups()) {
            validateOrderOptionGroup(orderOptionGroup, product);
        }
    }

    private void validateOrderOptionGroup(OrderOptionGroup orderOptionGroup, Product product) {

        ProductOptionGroup findProductOptionGroup = product.getProductOptionGroups().stream()
                .filter(e -> e.getName().equals(orderOptionGroup.getName()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("옵션그룹명이 변경됐습니다."));

        for (OrderOption orderOption : orderOptionGroup.getOrderOptions()) {

            ProductOption productOption = findProductOptionGroup.getProductOptions().stream()
                    .filter(e -> e.getName().equals(orderOption.getName()))
                    .findAny().orElseThrow(() -> new IllegalArgumentException("옵션명이 변경됐습니다."));

            if (productOption.getPrice() != orderOption.getPrice()) {
                throw new IllegalArgumentException("옵션 가격이 변경됐습니다.");
            }
        }
    }

    private Map<Long, Product> getProducts(Order order) {
        return productRepository.findAllById(order.getProductIds()).stream().collect(toMap(Product::getId, identity()));
    }
}
