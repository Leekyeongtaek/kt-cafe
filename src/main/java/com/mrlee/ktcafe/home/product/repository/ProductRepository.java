package com.mrlee.ktcafe.home.product.repository;

import com.mrlee.ktcafe.home.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.productOptionGroups WHERE p.id = :productId")
    Optional<Product> findByIdProductOptionGroupJoinFetch(@Param(value = "productId") Long productId);
}
