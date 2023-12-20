package com.mrlee.ktcafe.home.product.repository;

import com.mrlee.ktcafe.home.product.domain.ProductOptionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductOptionGroupRepository extends JpaRepository<ProductOptionGroup, Long> {

    @Query("SELECT pog FROM ProductOptionGroup pog LEFT JOIN FETCH pog.productOptions WHERE pog.id = :productOptionGroupId")
    Optional<ProductOptionGroup> findByIdProductOptionJoinPatch(@Param("productOptionGroupId") Long productOptionGroupId);
}
