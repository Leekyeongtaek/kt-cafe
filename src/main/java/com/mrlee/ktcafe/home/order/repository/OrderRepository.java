package com.mrlee.ktcafe.home.order.repository;

import com.mrlee.ktcafe.home.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.payment LEFT JOIN FETCH o.review WHERE o.id = :orderId")
    Optional<Order> findByIdPaymentAndReviewJoinFetch(@Param("orderId") Long orderId);
}
