package com.mrlee.ktcafe.home.order.repository;

import com.mrlee.ktcafe.home.order.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r LEFT JOIN FETCH r.member LEFT JOIN FETCH r.order")
    Page<Review> findAllMemberJoinFetch(Pageable pageable);
}
