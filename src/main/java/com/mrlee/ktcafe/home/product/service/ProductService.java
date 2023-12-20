package com.mrlee.ktcafe.home.product.service;

import com.mrlee.ktcafe.home.product.domain.ProductOption;
import com.mrlee.ktcafe.home.product.domain.ProductOptionGroup;
import com.mrlee.ktcafe.home.product.repository.ProductRepository;
import com.mrlee.ktcafe.home.product.repository.query.ProductQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.mrlee.ktcafe.home.product.service.dto.ProductSearchCond;
import com.mrlee.ktcafe.home.product.domain.Product;
import com.mrlee.ktcafe.home.product.repository.ProductOptionGroupRepository;
import com.mrlee.ktcafe.home.product.repository.ProductOptionRepository;
import com.mrlee.ktcafe.home.product.repository.query.dto.ProductQueryInquiryList;
import com.mrlee.ktcafe.home.product.service.dto.CreateProductInput;
import com.mrlee.ktcafe.home.product.service.dto.ProductInquiry;
import com.mrlee.ktcafe.home.product.service.dto.UpdateProductInput;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductQueryRepository productQueryRepository;
    private final ProductOptionGroupRepository productOptionGroupRepository;
    private final ProductOptionRepository productOptionRepository;

    public void addProduct(CreateProductInput createProductInput) {
        Product product = createProductInput.toProduct();
        try {
            productRepository.save(product);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("이미 존재하는 상품 이름 또는 상품옵션그룹 이름입니다.", e);
        }
    }

    public void modifyProduct(Long productId, UpdateProductInput updateProductInput) {
        Product product = productRepository.findByIdProductOptionGroupJoinFetch(productId).orElseThrow(IllegalArgumentException::new);
        try {
            product.update(updateProductInput);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("상품옵션그룹 또는 상품 옵션은 중복된 이름을 가질 수 없습니다.", e);
        }
    }

    public ProductInquiry findProduct(Long productId) {
        Product product = productRepository.findByIdProductOptionGroupJoinFetch(productId).orElseThrow(IllegalArgumentException::new);
        return new ProductInquiry(product);
    }

    public Page<ProductQueryInquiryList> searchProduct(Pageable pageable, ProductSearchCond productSearchCond) {
        return productQueryRepository.searchProduct(pageable, productSearchCond);
    }

    public void removeProductOptionGroup(Long productOptionGroupId) {
        ProductOptionGroup productOptionGroup = productOptionGroupRepository.findByIdProductOptionJoinPatch(productOptionGroupId).orElseThrow(IllegalArgumentException::new);
        productOptionGroupRepository.delete(productOptionGroup);
    }

    public void removeProductOption(Long productOptionGroupId, Long productOptionId) {
        List<ProductOption> productOptionList = productOptionRepository.findAllByProductOptionGroupId(productOptionGroupId);

        if (productOptionList.size() < 2) {
            throw new IllegalArgumentException("상품 옵션 그룹은 상품 옵션을 1개 이하로 삭제할 수 없습니다.");
        }

        ProductOption productOption = productOptionList.stream()
                .filter(e -> e.getId().equals(productOptionId))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);

        productOptionRepository.delete(productOption);
    }
}
