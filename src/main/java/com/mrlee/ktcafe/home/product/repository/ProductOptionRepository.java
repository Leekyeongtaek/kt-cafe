package com.mrlee.ktcafe.home.product.repository;

import com.mrlee.ktcafe.home.product.domain.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {

    List<ProductOption> findAllByProductOptionGroupId(Long productOptionGroupId);
}
