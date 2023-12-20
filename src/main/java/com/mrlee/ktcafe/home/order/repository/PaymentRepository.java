package com.mrlee.ktcafe.home.order.repository;

import com.mrlee.ktcafe.home.order.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
